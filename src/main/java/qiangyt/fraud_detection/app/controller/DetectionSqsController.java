/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.app.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.framework.aws.sqs.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionApi;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Service
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
public class DetectionSqsController {

    @Autowired SqsProps props;

    @Autowired SqsClient client;

    @Autowired Jackson jackson;

    @Autowired DetectionApi service;

    Thread pollingThread;

    AtomicBoolean polling = new AtomicBoolean();

    @PostConstruct
    public void start() {
        if (this.pollingThread != null) {
            throw new IllegalStateException("SQS detection queue polling thread ALREADY started");
        }

        this.pollingThread =
                new Thread(
                        () -> {
                            this.polling.set(true);
                            log.info("SQS detection queue polling: ready");

                            while (this.polling.get() && !Thread.currentThread().isInterrupted()) {
                                try {
                                    //if (log.isDebugEnabled()) {
                                        log.info("SQS detection queue polling: new polling");
                                    //}
                                    poll();
                                } catch (Exception ex) {
                                    log.error("Error in polling", ex);
                                }
                            }

                            log.info("SQS detection queue polling: end");
                        });

        this.pollingThread.start();
    }

    @PreDestroy
    public synchronized void stop() {
        if (this.pollingThread == null) {
            log.warn("SQS detection queue polling thread NOT started");
            return;
        }

        var th = this.pollingThread;
        this.pollingThread = null;

        log.info("Stops SQS detection queue polling: begin");
        this.polling.set(false);
        log.info("Stops SQS detection queue polling: waiting");
        try {
            th.join(5 * 1000); // TODO: have it configurable
        } catch (InterruptedException e) {
            // Thread.currentThread().interrupt();
        }

        if (th.isAlive()) {
            log.info("Stops SQS detection queue polling: force to stop it");
            th.interrupt();
            try {
                th.join();
            } catch (InterruptedException e) {
                // Thread.currentThread().interrupt();
            }
            log.info("Stops SQS detection queue polling: forced stopped");
        }

        log.info("Stops SQS detection queue polling: stopped");
    }

    // we don't use scheduled job, instead, we use a thread to poll the queue continuously and
    // depends on Sqs's long polling feature
    // @Scheduled(fixedRate = 5000) // 5 seconds polling interval
    public synchronized List<DetectionResult> poll() {
        var qurl = getProps().getQueueUrl();
        var serv = getService();

        var sqsReq =
                ReceiveMessageRequest.builder()
                        .queueUrl(qurl)
                        .maxNumberOfMessages(10) // Fetch 10 message at a time
                        .waitTimeSeconds(20) // Long polling
                        .build();

        var results = new ArrayList<DetectionResult>();

        for (var msg : client.receiveMessage(sqsReq).messages()) {
            String body = msg.body();
            log.info("Received message: " + body);

            var entity = getJackson().from(body, DetectionReqEntity.class);
            var result = serv.detect(entity);
            results.add(result);

            // Delete the message after processing it
            var sqsDeleteReq =
                    DeleteMessageRequest.builder()
                            .queueUrl(qurl)
                            .receiptHandle(msg.receiptHandle())
                            .build();
            client.deleteMessage(sqsDeleteReq);
            log.info("Deleted message from queue: " + msg.messageId());
        }

        return results;
    }
}

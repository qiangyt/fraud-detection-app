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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.config.SqsPollingProps;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.aws.sqs.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
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

    @Autowired DetectionService service;

    @Autowired ExecutorService sqsPollingThreadPool;

    @lombok.Getter(lombok.AccessLevel.PRIVATE)
    final AtomicBoolean polling = new AtomicBoolean();

    @Autowired SqsPollingProps pollingProps;

    @PostConstruct
    void start() {
        getSqsPollingThreadPool()
                .submit(
                        () -> {
                            getPolling().set(true);

                            log.info("SQS detection queue polling: started");

                            while (getPolling().get() && !Thread.currentThread().isInterrupted()) {
                                try {
                                    // if (log.isDebugEnabled()) {
                                    log.info("SQS detection queue polling: new polling");
                                    // }
                                    poll();
                                } catch (Exception ex) {
                                    log.error("Error in polling", ex);
                                }
                            }

                            log.info("SQS detection queue polling: stopped");
                        });
    }

    @PreDestroy
    void stop() {
        log.info("Stopping SQS detection queue polling");
        getPolling().set(false);

        getSqsPollingThreadPool().shutdown();
    }

    // we don't use scheduled job, instead, we use a thread to poll the queue
    // continuously and
    // depends on Sqs's long polling feature
    // @Scheduled(fixedRate = 5000) // 5 seconds polling interval
    void poll() {
        var qurl = getProps().getQueueUrl();
        var s = getService();
        var j = getJackson();

        var sqsReq =
                ReceiveMessageRequest.builder()
                        .queueUrl(qurl)
                        .maxNumberOfMessages(getPollingProps().getBatchSize())
                        .waitTimeSeconds(getPollingProps().getTimeout()) // Long polling
                        .build();

        for (var msg : client.receiveMessage(sqsReq).messages()) {
            String body = msg.body();
            // if (log.isDebugEnabled())
            log.info("Received message: " + body);

            var entity = j.from(body, DetectionReqEntity.class);
            s.detectAsync(entity);

            // Delete the message ;
            client.deleteMessage(
                    DeleteMessageRequest.builder()
                            .queueUrl(qurl)
                            .receiptHandle(msg.receiptHandle())
                            .build());
        }
    }
}

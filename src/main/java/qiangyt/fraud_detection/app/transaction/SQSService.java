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
package qiangyt.fraud_detection.app.transaction;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.framework.json.JacksonHelper;
import qiangyt.fraud_detection.sdk.model.Transaction;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Service
@lombok.extern.slf4j.Slf4j
public class SQSService {

    private final SqsClient sqsClient;
    private final String queueUrl;

    @Autowired
    public SQSService(SqsClient sqsClient, @Value("${aws.sqs.queue-url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    public void sendTransactionToQueue(Transaction transaction) {
        String message = JacksonHelper.to(transaction);

        SendMessageRequest sendMessageRequest =
                SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build();
        sqsClient.sendMessage(sendMessageRequest);
        log.debug("Message sent to SQS: " + message);
    }

    @Scheduled(fixedRate = 5000) // 5 seconds polling interval
    public List<Transaction> receiveTransactionFromQueue() {
        ReceiveMessageRequest receiveMessageRequest =
                ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(1) // Fetch 1 message at a time
                        .waitTimeSeconds(20) // Long polling
                        .build();

        List<Transaction> result = new ArrayList<>();
        ReceiveMessageResponse response = sqsClient.receiveMessage(receiveMessageRequest);

        for (Message message : response.messages()) {
            String body = message.body();
            log.debug("Received message: " + body);

            result.add(JacksonHelper.from(body, Transaction.class));

            // Delete the message after processing it
            DeleteMessageRequest deleteMessageRequest =
                    DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build();
            sqsClient.deleteMessage(deleteMessageRequest);
            log.debug("Deleted message from queue: " + message.messageId());
        }

        return result;
    }
}

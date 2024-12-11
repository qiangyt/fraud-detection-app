package qiangyt.fraud_detection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractITTest extends AbstractIT {

    @BeforeEach
    public void setUp() {
        clearQueue(sqsProps.getAlertQueueUrl());
    }

    /**
     * Test case to verify that the pollAlert method returns null when no message is in the queue.
     */
    @Test
    public void testPollAlertNoMessage() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNull(result, "Expected null when no message is in the queue");
    }

    /**
     * Test case to verify that the pollAlert method returns a valid DetectionResult object when a message is in the queue.
     */
    @Test
    public void testPollAlertWithMessage() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        String body = "{\"id\":1,\"score\":0.9}";
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(body)
                .build());

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNotNull(result, "Expected a valid DetectionResult object when a message is in the queue");
        assertEquals(1, result.getId(), "Incorrect id in DetectionResult");
        assertEquals(0.9, result.getScore(), 0.001, "Incorrect score in DetectionResult");
    }

    /**
     * Test case to verify that the pollAlert method deletes the message from the queue after processing.
     */
    @Test
    public void testPollAlertDeletesMessage() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        String body = "{\"id\":1,\"score\":0.9}";
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(body)
                .build());

        // Act
        pollAlert();

        // Assert
        var req = ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .maxNumberOfMessages(sqsProps.getBatchSize())
                .waitTimeSeconds(0)
                .build();
        assertEquals(0, sqsClient.receiveMessage(req).messages().size(), "Expected no messages in the queue after processing");
    }

    /**
     * Test case to verify that the pollAlert method handles an empty message body gracefully.
     */
    @Test
    public void testPollAlertEmptyMessageBody() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody("")
                .build());

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNull(result, "Expected null when message body is empty");
    }

    /**
     * Test case to verify that the pollAlert method handles a malformed message body gracefully.
     */
    @Test
    public void testPollAlertMalformedMessageBody() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody("invalid json")
                .build());

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNull(result, "Expected null when message body is malformed");
    }

    /**
     * Test case to verify that the pollAlert method handles a large message body gracefully.
     */
    @Test
    public void testPollAlertLargeMessageBody() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        StringBuilder body = new StringBuilder("{\"id\":1,\"score\":0.9");
        for (int i = 0; i < 1000; i++) {
            body.append(",\"field").append(i).append("\":\"value").append(i).append("\"}");
        }
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(body.toString())
                .build());

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNotNull(result, "Expected a valid DetectionResult object when a large message is in the queue");
    }

    /**
     * Test case to verify that the pollAlert method handles a null message body gracefully.
     */
    @Test
    public void testPollAlertNullMessageBody() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(null)
                .build());

        // Act
        DetectionResult result = pollAlert();

        // Assert
        assertNull(result, "Expected null when message body is null");
    }

    /**
     * Test case to verify that the pollAlert method handles a message with an empty receipt handle gracefully.
     */
    @Test
    public void testPollAlertEmptyReceiptHandle() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        String body = "{\"id\":1,\"score\":0.9}";
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(body)
                .build());

        // Act
        pollAlert();

        // Assert
        var req = ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .maxNumberOfMessages(sqsProps.getBatchSize())
                .waitTimeSeconds(0)
                .build();
        assertEquals(1, sqsClient.receiveMessage(req).messages().size(), "Expected one message in the queue after processing");
    }

    /**
     * Test case to verify that the pollAlert method handles a message with a null receipt handle gracefully.
     */
    @Test
    public void testPollAlertNullReceiptHandle() {
        // Arrange
        String alertQueueUrl = sqsProps.getAlertQueueUrl();
        String body = "{\"id\":1,\"score\":0.9}";
        sqsClient.sendMessage(ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .messageBody(body)
                .build());

        // Act
        pollAlert();

        // Assert
        var req = ReceiveMessageRequest.builder()
                .queueUrl(alertQueueUrl)
                .maxNumberOfMessages(sqsProps.getBatchSize())
                .waitTimeSeconds(0)
                .build();
        assertEquals(1, sqsClient.receiveMessage(req).messages().size(), "Expected one message in the queue after processing");
    }
}

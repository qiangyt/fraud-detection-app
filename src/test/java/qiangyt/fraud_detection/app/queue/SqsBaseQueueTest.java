package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SqsBaseQueueTest {

    private SqsClient sqsClientMock;
    private Jackson jacksonMock;
    private StringHelper stringHelperMock;

    @BeforeEach
    public void setUp() {
        sqsClientMock = mock(SqsClient.class);
        jacksonMock = mock(Jackson.class);
        stringHelperMock = mock(StringHelper.class);

        SqsBaseQueue<String> queue = new SqsBaseQueue<String>() {};
        queue.setClient(sqsClientMock);
        queue.setJackson(jacksonMock);
        queue.setStringHelper(stringHelperMock);
    }

    @Test
    public void testSendHappyPath() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/myqueue";
        String data = "testData";
        when(jacksonMock.str(data)).thenReturn("{\"key\":\"value\"}");

        // Act
        queue.send(queueUrl, data, null, null);

        // Assert
        verify(sqsClientMock).sendMessage(any(SendMessageRequest.class));
    }

    @Test
    public void testSendWithDeduplicationId() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/myqueue";
        String data = "testData";
        String deduplicationId = "dedupId";
        when(jacksonMock.str(data)).thenReturn("{\"key\":\"value\"}");

        // Act
        queue.send(queueUrl, data, deduplicationId, null);

        // Assert
        verify(sqsClientMock).sendMessage(any(SendMessageRequest.class));
    }

    @Test
    public void testSendWithMessageGroupId() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/myqueue";
        String data = "testData";
        String messageGroupId = "groupId";
        when(jacksonMock.str(data)).thenReturn("{\"key\":\"value\"}");

        // Act
        queue.send(queueUrl, data, null, messageGroupId);

        // Assert
        verify(sqsClientMock).sendMessage(any(SendMessageRequest.class));
    }

    @Test
    public void testSendWithDeduplicationIdAndMessageGroupId() {
        // Arrange
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/myqueue";
        String data = "testData";
        String deduplicationId = "dedupId";
        String messageGroupId = "groupId";
        when(jacksonMock.str(data)).thenReturn("{\"key\":\"value\"}");

        // Act
        queue.send(queueUrl, data, deduplicationId, messageGroupId);

        // Assert
        verify(sqsClientMock).sendMessage(any(SendMessageRequest.class));
    }
}

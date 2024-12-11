package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SqsDetectionQueueTest {

    private SqsDetectionQueue sqsDetectionQueue;
    private SqsBaseQueue mockSqsBaseQueue;

    @BeforeEach
    public void setUp() {
        mockSqsBaseQueue = mock(SqsBaseQueue.class);
        sqsDetectionQueue = new SqsDetectionQueue();
        sqsDetectionQueue.setProps(mockSqsBaseQueue.getProps());
        sqsDetectionQueue.setSqsClient(mockSqsBaseQueue.getSqsClient());
    }

    @Test
    public void testSend_HappyPath() {
        // Arrange
        DetectionReqEntity req = new DetectionReqEntity();
        String queueUrl = "http://example.com/queue";
        when(mockSqsBaseQueue.getDetectQueueUrl()).thenReturn(queueUrl);

        // Act
        sqsDetectionQueue.send(req);

        // Assert
        verify(mockSqsBaseQueue, times(1)).send(queueUrl, req, "", "");
    }

    @Test
    public void testSend_NullRequest() {
        // Arrange
        when(mockSqsBaseQueue.getDetectQueueUrl()).thenReturn("http://example.com/queue");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sqsDetectionQueue.send(null));
    }

    @Test
    public void testSend_EmptyQueueUrl() {
        // Arrange
        DetectionReqEntity req = new DetectionReqEntity();
        when(mockSqsBaseQueue.getDetectQueueUrl()).thenReturn("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sqsDetectionQueue.send(req));
    }

    @Test
    public void testSend_InvalidQueueUrl() {
        // Arrange
        DetectionReqEntity req = new DetectionReqEntity();
        when(mockSqsBaseQueue.getDetectQueueUrl()).thenReturn("invalid://queue");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sqsDetectionQueue.send(req));
    }
}

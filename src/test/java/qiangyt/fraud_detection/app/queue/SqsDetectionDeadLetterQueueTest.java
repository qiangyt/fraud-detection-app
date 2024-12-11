import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SqsDetectionDeadLetterQueueTest {

    private SqsDetectionDeadLetterQueue sqsDetectionDeadLetterQueue;
    private SqsBaseQueue<String> mockSqsBaseQueue;

    @BeforeEach
    public void setUp() {
        mockSqsBaseQueue = mock(SqsBaseQueue.class);
        sqsDetectionDeadLetterQueue = new SqsDetectionDeadLetterQueue();
        sqsDetectionDeadLetterQueue.setProps(mock(SqsProperties.class));
    }

    /**
     * Test case to verify that the send method correctly sends a message to the SQS detection queue.
     */
    @Test
    public void testSendHappyPath() {
        // Arrange
        String msg = "test message";
        when(mockSqsBaseQueue.getProps()).thenReturn(mock(SqsProperties.class));
        when(mockSqsBaseQueue.getProps().getDetectDeadLetterQueueUrl()).thenReturn("http://example.com/queue");

        // Act
        sqsDetectionDeadLetterQueue.send(msg);

        // Assert
        verify(mockSqsBaseQueue, times(1)).send(eq("http://example.com/queue"), eq(msg), eq(""), eq(""));
    }

    /**
     * Test case to verify that the send method throws an IllegalArgumentException when the message is null.
     */
    @Test
    public void testSendNullMessage() {
        // Arrange
        String msg = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sqsDetectionDeadLetterQueue.send(msg));
    }

    /**
     * Test case to verify that the send method throws an IllegalArgumentException when the message is empty.
     */
    @Test
    public void testSendEmptyMessage() {
        // Arrange
        String msg = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sqsDetectionDeadLetterQueue.send(msg));
    }

    /**
     * Test case to verify that the send method correctly handles a corner case where the queue URL is null.
     */
    @Test
    public void testSendNullQueueUrl() {
        // Arrange
        String msg = "test message";
        when(mockSqsBaseQueue.getProps()).thenReturn(mock(SqsProperties.class));
        when(mockSqsBaseQueue.getProps().getDetectDeadLetterQueueUrl()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sqsDetectionDeadLetterQueue.send(msg));
    }

    /**
     * Test case to verify that the send method correctly handles a corner case where the queue URL is empty.
     */
    @Test
    public void testSendEmptyQueueUrl() {
        // Arrange
        String msg = "test message";
        when(mockSqsBaseQueue.getProps()).thenReturn(mock(SqsProperties.class));
        when(mockSqsBaseQueue.getProps().getDetectDeadLetterQueueUrl()).thenReturn("");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sqsDetectionDeadLetterQueue.send(msg));
    }

    /**
     * Test case to verify that the send method correctly handles a corner case where the queue URL is invalid.
     */
    @Test
    public void testSendInvalidQueueUrl() {
        // Arrange
        String msg = "test message";
        when(mockSqsBaseQueue.getProps()).thenReturn(mock(SqsProperties.class));
        when(mockSqsBaseQueue.getProps().getDetectDeadLetterQueueUrl()).thenReturn("invalid://queue");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sqsDetectionDeadLetterQueue.send(msg));
    }
}

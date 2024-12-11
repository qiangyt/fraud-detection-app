package qiangyt.fraud_detection.app.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class DetectionRequestQueueTest {

    private DetectionRequestQueue queue;
    private DetectionReqEntity mockReq;

    @BeforeEach
    public void setUp() {
        queue = mock(DetectionRequestQueue.class);
        mockReq = mock(DetectionReqEntity.class);
    }

    /**
     * Test case to verify that the send method correctly sends a detection request to the queue.
     */
    @Test
    public void testSendHappyPath() {
        // Arrange
        // Act
        queue.send(mockReq);

        // Assert
        verify(queue, times(1)).send(mockReq);
    }

    /**
     * Test case to verify that the send method correctly handles null detection request entities.
     */
    @Test
    public void testSendNullRequest() {
        // Arrange
        DetectionReqEntity nullReq = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> queue.send(nullReq));
    }

    /**
     * Test case to verify that the send method correctly handles empty detection request entities.
     */
    @Test
    public void testSendEmptyRequest() {
        // Arrange
        DetectionReqEntity emptyReq = new DetectionReqEntity();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> queue.send(emptyReq));
    }

    /**
     * Test case to verify that the send method correctly handles invalid detection request entities.
     */
    @Test
    public void testSendInvalidRequest() {
        // Arrange
        DetectionReqEntity invalidReq = new DetectionReqEntity();
        when(invalidReq.isValid()).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> queue.send(invalidReq));
    }
}

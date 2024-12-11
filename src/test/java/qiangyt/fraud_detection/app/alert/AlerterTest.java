package qiangyt.fraud_detection.app.alert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AlerterTest {

    private Alerter alerter;
    private DetectionResult mockDetectionResult;

    @BeforeEach
    public void setUp() {
        alerter = mock(Alerter.class);
        mockDetectionResult = mock(DetectionResult.class);
    }

    /**
     * Test case to verify that the send method is called with a non-null detection result.
     */
    @Test
    public void testSendWithNonNullDetectionResult() {
        // Arrange
        DetectionResult alert = mockDetectionResult;

        // Act
        alerter.send(alert);

        // Assert
        verify(alerter, times(1)).send(alert);
    }

    /**
     * Test case to verify that the send method is not called with a null detection result.
     */
    @Test
    public void testSendWithNullDetectionResult() {
        // Arrange
        DetectionResult alert = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> alerter.send(alert));
    }

    /**
     * Test case to verify that the send method is called with a detection result having a non-empty message.
     */
    @Test
    public void testSendWithNonEmptyMessage() {
        // Arrange
        DetectionResult alert = mockDetectionResult;
        when(alert.getMessage()).thenReturn("Fraud detected");

        // Act
        alerter.send(alert);

        // Assert
        verify(alerter, times(1)).send(alert);
    }

    /**
     * Test case to verify that the send method is called with a detection result having an empty message.
     */
    @Test
    public void testSendWithEmptyMessage() {
        // Arrange
        DetectionResult alert = mockDetectionResult;
        when(alert.getMessage()).thenReturn("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> alerter.send(alert));
    }
}

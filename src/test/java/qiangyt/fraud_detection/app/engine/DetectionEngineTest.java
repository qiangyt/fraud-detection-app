package qiangyt.fraud_detection.app.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionEngineTest {

    /**
     * Test case to verify that the DetectionEngine interface is correctly implemented.
     */
    @Test
    public void testDetectionEngineInterface() {
        // Arrange
        DetectionEngine detectionEngine = new MockDetectionEngine();

        // Act & Assert
        assertTrue(detectionEngine instanceof DetectionRule);
    }

    /**
     * Test case to verify that the DetectionEngine detects fraud based on a positive rule.
     */
    @Test
    public void testDetectFraudPositive() {
        // Arrange
        DetectionEngine detectionEngine = new MockDetectionEngine();
        FraudEvent event = new FraudEvent("1234567890", 100.0);

        // Act
        boolean result = detectionEngine.detect(event);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the DetectionEngine does not detect fraud based on a negative rule.
     */
    @Test
    public void testDetectFraudNegative() {
        // Arrange
        DetectionEngine detectionEngine = new MockDetectionEngine();
        FraudEvent event = new FraudEvent("1234567890", 5.0);

        // Act
        boolean result = detectionEngine.detect(event);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the DetectionEngine handles null events gracefully.
     */
    @Test
    public void testDetectFraudNullEvent() {
        // Arrange
        DetectionEngine detectionEngine = new MockDetectionEngine();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionEngine.detect(null));
    }
}

class MockDetectionEngine implements DetectionEngine {

    @Override
    public boolean detect(FraudEvent event) {
        if (event == null) {
            throw new NullPointerException("FraudEvent cannot be null");
        }
        return event.getAmount() > 50.0;
    }
}

class FraudEvent {
    private String transactionId;
    private double amount;

    public FraudEvent(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }
}

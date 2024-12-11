package qiangyt.fraud_detection.app.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DroolsRuleEngineTest {

    private DroolsRuleEngine droolsRuleEngine;

    @BeforeEach
    public void setUp() {
        droolsRuleEngine = new DroolsRuleEngine();
    }

    /**
     * Test case to verify the detect method returns NONE when no fraud rules are applied.
     */
    @Test
    public void testDetect_NoFraudRulesApplied() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();

        // Act
        FraudCategory result = droolsRuleEngine.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case to verify the detect method returns a valid fraud category when fraud rules are applied.
     */
    @Test
    public void testDetect_FraudRulesApplied() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        // Simulate applying fraud rules and setting the detected fraud category
        droolsRuleEngine.setDetectedFraudCategory(FraudCategory.LOW);

        // Act
        FraudCategory result = droolsRuleEngine.detect(entity);

        // Assert
        assertEquals(FraudCategory.LOW, result);
    }

    /**
     * Test case to verify the detect method handles null input gracefully.
     */
    @Test
    public void testDetect_NullInput() {
        // Arrange
        DetectionReqEntity entity = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> droolsRuleEngine.detect(entity));
    }

    /**
     * Test case to verify the detect method handles empty input gracefully.
     */
    @Test
    public void testDetect_EmptyInput() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        // Simulate applying fraud rules and setting the detected fraud category
        droolsRuleEngine.setDetectedFraudCategory(FraudCategory.MEDIUM);

        // Act
        FraudCategory result = droolsRuleEngine.detect(entity);

        // Assert
        assertEquals(FraudCategory.MEDIUM, result);
    }
}

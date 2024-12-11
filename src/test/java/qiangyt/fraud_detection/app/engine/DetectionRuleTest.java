!!!!test_begin!!!!

package qiangyt.fraud_detection.app.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetectionRuleTest {

    @Test
    public void testDetectHappyPath() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000);
        entity.setTransactionType("purchase");
        
        DetectionRule rule = (entity) -> FraudCategory.NONE;

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetectNegativeAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(-100);
        entity.setTransactionType("purchase");
        
        DetectionRule rule = (entity) -> FraudCategory.NONE;

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetectLargeAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000000);
        entity.setTransactionType("purchase");
        
        DetectionRule rule = (entity) -> FraudCategory.NONE;

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetectUnknownTransactionType() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000);
        entity.setTransactionType("unknown");
        
        DetectionRule rule = (entity) -> FraudCategory.NONE;

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }
}
!!!!test_end!!!!

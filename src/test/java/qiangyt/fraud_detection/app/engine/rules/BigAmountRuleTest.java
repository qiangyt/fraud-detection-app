package qiangyt.fraud_detection.app.engine.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BigAmountRuleTest {

    private BigAmountRule bigAmountRule;
    private RuleProps ruleProps;
    private ChainedDetectionEngine chain;

    @BeforeEach
    public void setUp() {
        ruleProps = mock(RuleProps.class);
        chain = mock(ChainedDetectionEngine.class);
        bigAmountRule = new BigAmountRule();
        bigAmountRule.setProps(ruleProps);
        bigAmountRule.setChain(chain);

        // Initialize the rule to add it to the detection engine chain
        bigAmountRule.init();
    }

    @Test
    public void testDetect_HappyPath() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(500.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result);
    }

    @Test
    public void testDetect_MaxTransactionAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(500.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(500.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetect_NegativeAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(-100.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(500.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetect_ZeroAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(0.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(500.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetect_MaxTransactionAmountNegative() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(-500.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetect_MaxTransactionAmountZero() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(1000.0);
        when(ruleProps.getMaxTransactionAmount()).thenReturn(0.0);

        // Act
        FraudCategory result = bigAmountRule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }
}

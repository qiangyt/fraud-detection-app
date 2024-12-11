package qiangyt.fraud_detection.app.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChainedDetectionEngineTest {

    private ChainedDetectionEngine engine;
    private BigAmountRule bigAmountRule;
    private SuspiciousAccountRule suspiciousAccountRule;

    @BeforeEach
    public void setUp() {
        engine = new ChainedDetectionEngine();
        bigAmountRule = mock(BigAmountRule.class);
        suspiciousAccountRule = mock(SuspiciousAccountRule.class);

        engine.addRule(bigAmountRule);
        engine.addRule(suspiciousAccountRule);
    }

    @Test
    public void testDetect_FraudCategoryBigAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.BIG_AMOUNT);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule, never()).detect(entity);
    }

    @Test
    public void testDetect_FraudCategorySuspiciousAccount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.SUSPICIOUS_ACCOUNT);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule).detect(entity);
    }

    @Test
    public void testDetect_FraudCategoryNone() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.NONE);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule).detect(entity);
    }

    @Test
    public void testDetect_MultipleRules() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.NONE);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.BIG_AMOUNT);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule).detect(entity);
    }

    @Test
    public void testDetect_NullEntity() {
        // Arrange
        DetectionReqEntity entity = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> engine.detect(entity));
    }

    @Test
    public void testDetect_BigAmountRuleReturnsNull() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(null);
        when(suspiciousAccountRule.detect(entity)).thenReturn(FraudCategory.SUSPICIOUS_ACCOUNT);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule).detect(entity);
    }

    @Test
    public void testDetect_SuspiciousAccountRuleReturnsNull() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(FraudCategory.BIG_AMOUNT);
        when(suspiciousAccountRule.detect(entity)).thenReturn(null);

        // Act
        FraudCategory result = engine.detect(entity);

        // Assert
        assertEquals(FraudCategory.BIG_AMOUNT, result);
        verify(bigAmountRule).detect(entity);
        verify(suspiciousAccountRule).detect(entity);
    }

    @Test
    public void testDetect_AllRulesReturnNull() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        when(bigAmountRule.detect(entity)).thenReturn(null);
        when(suspiciousAccountRule.detect(entity)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> engine.detect(entity));
    }
}

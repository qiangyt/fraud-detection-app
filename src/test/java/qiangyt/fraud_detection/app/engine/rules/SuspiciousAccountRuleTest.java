package qiangyt.fraud_detection.app.engine.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

@SpringBootTest
public class SuspiciousAccountRuleTest {

    @Autowired
    private SuspiciousAccountRule rule;

    @Autowired
    private RuleProps props;

    @BeforeEach
    public void setUp() {
        // Initialize the rule with a mock configuration
        props.getSuspiciousAccounts().clear();
    }

    /**
     * Test case for detecting a suspicious account.
     */
    @Test
    public void testDetectSuspiciousAccount() {
        // Arrange
        String accountId = "12345";
        props.getSuspiciousAccounts().add(accountId);
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(accountId);

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }

    /**
     * Test case for detecting a non-suspicious account.
     */
    @Test
    public void testDetectNonSuspiciousAccount() {
        // Arrange
        String accountId = "12345";
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(accountId);

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for handling an empty account ID.
     */
    @Test
    public void testDetectEmptyAccountId() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId("");

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for handling a null account ID.
     */
    @Test
    public void testDetectNullAccountId() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId(null);

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.NONE, result);
    }

    /**
     * Test case for handling a large number of suspicious accounts.
     */
    @Test
    public void testDetectLargeNumberOfSuspiciousAccounts() {
        // Arrange
        for (int i = 0; i < 1000; i++) {
            props.getSuspiciousAccounts().add("account" + i);
        }
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAccountId("account500");

        // Act
        FraudCategory result = rule.detect(entity);

        // Assert
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }
}

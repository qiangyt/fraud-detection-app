package qiangyt.fraud_detection.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RuleProps.class)
public class RulePropsTest {

    private RuleProps ruleProps;

    @BeforeEach
    public void setUp() {
        ruleProps = new RuleProps();
    }

    /**
     * Test case to verify the default values of maxTransactionAmount and suspiciousAccounts.
     */
    @Test
    public void testDefaultValues() {
        assertEquals(100000, ruleProps.getMaxTransactionAmount());
        assertEquals(Set.of("cgrant", "fbiden"), ruleProps.getSuspiciousAccounts());
    }

    /**
     * Test case to verify the setting of maxTransactionAmount.
     */
    @Test
    public void testMaxTransactionAmount() {
        ruleProps.setMaxTransactionAmount(50000);
        assertEquals(50000, ruleProps.getMaxTransactionAmount());
    }

    /**
     * Test case to verify the setting of suspiciousAccounts.
     */
    @Test
    public void testSuspiciousAccounts() {
        Set<String> newSuspiciousAccounts = Set.of("newuser", "anotheruser");
        ruleProps.setSuspiciousAccounts(newSuspiciousAccounts);
        assertEquals(newSuspiciousAccounts, ruleProps.getSuspiciousAccounts());
    }

    /**
     * Test case to verify the behavior when setting maxTransactionAmount to a negative value.
     */
    @Test
    public void testMaxTransactionAmountNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            ruleProps.setMaxTransactionAmount(-1);
        });
    }

    /**
     * Test case to verify the behavior when setting suspiciousAccounts to null.
     */
    @Test
    public void testSuspiciousAccountsNull() {
        assertThrows(NullPointerException.class, () -> {
            ruleProps.setSuspiciousAccounts(null);
        });
    }
}

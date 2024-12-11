package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FraudCategoryTest {

    /**
     * Test the NONE category.
     */
    @Test
    public void testNONE() {
        // Given
        FraudCategory category = FraudCategory.NONE;

        // Then
        assertFalse(category.yes);
        assertEquals("none", category.message);
    }

    /**
     * Test the BIG_AMOUNT category.
     */
    @Test
    public void testBIG_AMOUNT() {
        // Given
        FraudCategory category = FraudCategory.BIG_AMOUNT;

        // Then
        assertTrue(category.yes);
        assertEquals("the transaction amount exceeds a threshold", category.message);
    }

    /**
     * Test the SUSPICIOUS_ACCOUNT category.
     */
    @Test
    public void testSUSPICIOUS_ACCOUNT() {
        // Given
        FraudCategory category = FraudCategory.SUSPICIOUS_ACCOUNT;

        // Then
        assertTrue(category.yes);
        assertEquals("the transaction originates from a suspicious account", category.message);
    }

    /**
     * Test the NONE category with null values.
     */
    @Test
    public void testNONEWithNullValues() {
        // Given
        FraudCategory category = FraudCategory.NONE;

        // Then
        assertNull(category.yes);
        assertNull(category.message);
    }

    /**
     * Test the BIG_AMOUNT category with null values.
     */
    @Test
    public void testBIG_AMOUNTWithNullValues() {
        // Given
        FraudCategory category = FraudCategory.BIG_AMOUNT;

        // Then
        assertNull(category.yes);
        assertNull(category.message);
    }

    /**
     * Test the SUSPICIOUS_ACCOUNT category with null values.
     */
    @Test
    public void testSUSPICIOUS_ACCOUNTWithNullValues() {
        // Given
        FraudCategory category = FraudCategory.SUSPICIOUS_ACCOUNT;

        // Then
        assertNull(category.yes);
        assertNull(category.message);
    }
}

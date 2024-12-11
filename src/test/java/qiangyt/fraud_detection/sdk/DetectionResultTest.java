package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionResultTest {

    private DetectionReqEntity entity;
    private FraudCategory category;

    @BeforeEach
    public void setUp() {
        entity = new DetectionReqEntity();
        category = FraudCategory.CREDIT_CARD_FRAUD;
    }

    /**
     * Test the from method with valid parameters.
     */
    @Test
    public void testFromValidParameters() {
        DetectionResult result = DetectionResult.from(entity, category);

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertTrue(result.isFraudulent());
        assertEquals(category, result.getCategory());
        assertEquals(category.message, result.getMessage());
        assertNotNull(result.getDetectedAt());
    }

    /**
     * Test the from method with null entity.
     */
    @Test
    public void testFromNullEntity() {
        assertThrows(NullPointerException.class, () -> DetectionResult.from(null, category));
    }

    /**
     * Test the from method with null category.
     */
    @Test
    public void testFromNullCategory() {
        assertThrows(NullPointerException.class, () -> DetectionResult.from(entity, null));
    }

    /**
     * Test the from method with empty message in category.
     */
    @Test
    public void testFromEmptyMessageInCategory() {
        FraudCategory categoryWithEmptyMessage = new FraudCategory(false, "");
        DetectionResult result = DetectionResult.from(entity, categoryWithEmptyMessage);

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertFalse(result.isFraudulent());
        assertEquals(categoryWithEmptyMessage, result.getCategory());
        assertEquals("", result.getMessage());
        assertNotNull(result.getDetectedAt());
    }

    /**
     * Test the from method with null message in category.
     */
    @Test
    public void testFromNullMessageInCategory() {
        FraudCategory categoryWithNullMessage = new FraudCategory(false, null);
        DetectionResult result = DetectionResult.from(entity, categoryWithNullMessage);

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertFalse(result.isFraudulent());
        assertEquals(categoryWithNullMessage, result.getCategory());
        assertNull(result.getMessage());
        assertNotNull(result.getDetectedAt());
    }

    /**
     * Test the from method with null detectedAt.
     */
    @Test
    public void testFromNullDetectedAt() {
        DetectionResult result = DetectionResult.builder()
                .id(UuidHelper.shortUuid())
                .entity(entity)
                .fraudulent(category.yes)
                .category(category)
                .message(category.message)
                .detectedAt(null)
                .build();

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertTrue(result.isFraudulent());
        assertEquals(category, result.getCategory());
        assertEquals(category.message, result.getMessage());
        assertNull(result.getDetectedAt());
    }
}

package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionApiTest {

    private DetectionApi detectionApi;

    @BeforeEach
    public void setUp() {
        // Initialize the DetectionApi instance here if needed
        detectionApi = new MockDetectionApi(); // Assuming a mock implementation exists
    }

    /**
     * Test case for submitting a valid detection request.
     */
    @Test
    public void testSubmitValidRequest() {
        // Arrange
        DetectionReq req = new DetectionReq();
        req.setTransactionId("12345");
        req.setAmount(100.0);

        // Act
        DetectionReqEntity result = detectionApi.submit(req);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getTransactionId());
        assertEquals(100.0, result.getAmount());
    }

    /**
     * Test case for submitting a null detection request.
     */
    @Test
    public void testSubmitNullRequest() {
        // Arrange
        DetectionReq req = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionApi.submit(req));
    }

    /**
     * Test case for detecting fraud with valid data.
     */
    @Test
    public void testDetectFraudValidData() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("67890");
        entity.setAmount(500.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertFalse(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with null data.
     */
    @Test
    public void testDetectFraudNullData() {
        // Arrange
        DetectionReqEntity entity = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionApi.detect(entity));
    }

    /**
     * Test case for detecting fraud with an amount that exceeds the threshold.
     */
    @Test
    public void testDetectFraudHighAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("11223");
        entity.setAmount(1000.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertTrue(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with an amount that is exactly at the threshold.
     */
    @Test
    public void testDetectFraudThresholdAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("44556");
        entity.setAmount(500.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertFalse(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with an amount that is below the threshold.
     */
    @Test
    public void testDetectFraudLowAmount() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("77889");
        entity.setAmount(10.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertFalse(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with a transaction ID that is too short.
     */
    @Test
    public void testDetectFraudShortTransactionId() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("123");
        entity.setAmount(500.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertTrue(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with a transaction ID that is too long.
     */
    @Test
    public void testDetectFraudLongTransactionId() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setTransactionId("12345678901234567890");
        entity.setAmount(500.0);

        // Act
        DetectionResult result = detectionApi.detect(entity);

        // Assert
        assertNotNull(result);
        assertTrue(result.isFraudulent());
    }

    /**
     * Test case for detecting fraud with a transaction ID that is null.
     */
    @Test
    public void testDetectFraudNullTransactionId() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        entity.setAmount(500.0);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionApi.detect(entity));
    }
}

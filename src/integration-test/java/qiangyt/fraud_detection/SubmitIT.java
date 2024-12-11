package qiangyt.fraud_detection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubmitIT extends AbstractIT {

    @BeforeEach 
    @AfterEach
    public void aroundTest() {
        var detectQueueUrl = sqsProps.getDetectQueueUrl();
        clearQueue(detectQueueUrl);

        var alertQueueUrl = sqsProps.getAlertQueueUrl();
        clearQueue(alertQueueUrl);
    }

    /**
     * Test case to verify that a non-fraud request is submitted successfully and no alert is generated.
     */
    @Test
    public void testSubmitNonFraudRequest() {
        // Arrange
        var nonFraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999)
                    .memo("N/A")
                    .build();

        // Act
        var nonFraudEntity = appClient.submit(nonFraudReq);

        // Assert
        assertNotNull(nonFraudEntity);
        assertNull(pollAlert());
    }

    /**
     * Test case to verify that a fraud request is submitted successfully and an alert is generated.
     */
    @Test
    public void testSubmitFraudRequest() {
        // Arrange
        var fraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999 * 100000)
                    .memo("N/A")
                    .build();

        // Act
        var fraudEntity = appClient.submit(fraudReq);

        // Assert
        assertNotNull(fraudEntity);
        assertNotNull(pollAlert());
    }

    /**
     * Test case to verify that submitting a request with an empty account ID results in null.
     */
    @Test
    public void testSubmitEmptyAccountId() {
        // Arrange
        var req = DetectionReq.builder()
                    .accountId("")
                    .amount(999)
                    .memo("N/A")
                    .build();

        // Act
        var entity = appClient.submit(req);

        // Assert
        assertNull(entity);
    }

    /**
     * Test case to verify that submitting a request with a negative amount results in null.
     */
    @Test
    public void testSubmitNegativeAmount() {
        // Arrange
        var req = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(-999)
                    .memo("N/A")
                    .build();

        // Act
        var entity = appClient.submit(req);

        // Assert
        assertNull(entity);
    }
}

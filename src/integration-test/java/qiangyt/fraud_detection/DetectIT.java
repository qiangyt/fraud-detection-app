package qiangyt.fraud_detection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;

/**
 * Integration tests for the fraud detection application.
 */
public class DetectIT extends AbstractIT {

    /**
     * Main method for debugging integration tests.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        var t = new DetectIT();
        t.testDetect();
    }

    /**
     * Tests the fraud detection functionality by sending both non-fraud and fraud requests.
     */
    @Test
    public void testDetect() {
        // first, sends a non-fraud request
        var nonFraudReq = DetectionReqEntity.builder()
                    .id("integration-test-1")
                    .accountId("integration-test-account-1")
                    .amount(999)
                    .memo("N/A")
                    .build();
        var nonFraudResult = appClient.detect(nonFraudReq);        
        dump("non-fraud: ", nonFraudResult);

        // not a fraud transaction
        assertFalse(nonFraudResult.isFraudulent());

        // second, sends a fraud request
        var fraudReq = DetectionReqEntity.builder()
                    .id("integration-test-2")
                    .accountId("fbiden")
                    .amount(999)
                    .memo("N/A")
                    .build();
        var fraudResult = appClient.detect(fraudReq);   
        dump("fraud: ", fraudResult);

         // is a fraud transaction
        assertTrue(fraudResult.isFraudulent());
    }

    /**
     * Tests the fraud detection functionality with an empty account ID.
     */
    @Test
    public void testDetectEmptyAccountId() {
        // sends a request with an empty account ID
        var req = DetectionReqEntity.builder()
                    .id("integration-test-3")
                    .accountId("")
                    .amount(999)
                    .memo("N/A")
                    .build();
        var result = appClient.detect(req);        
        dump("empty account ID: ", result);

        // should not be a fraud transaction
        assertFalse(result.isFraudulent());
    }

    /**
     * Tests the fraud detection functionality with an invalid amount.
     */
    @Test
    public void testDetectInvalidAmount() {
        // sends a request with an invalid amount
        var req = DetectionReqEntity.builder()
                    .id("integration-test-4")
                    .accountId("invalid-account-id")
                    .amount(-1)
                    .memo("N/A")
                    .build();
        var result = appClient.detect(req);        
        dump("invalid amount: ", result);

        // should not be a fraud transaction
        assertFalse(result.isFraudulent());
    }

    /**
     * Tests the fraud detection functionality with a null request.
     */
    @Test
    public void testDetectNullRequest() {
        // sends a null request
        var result = appClient.detect(null);        
        dump("null request: ", result);

        // should not be a fraud transaction
        assertFalse(result.isFraudulent());
    }
}

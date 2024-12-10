/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

}
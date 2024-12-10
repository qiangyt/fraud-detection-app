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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import qiangyt.fraud_detection.sdk.DetectionReq;

public class SubmitIT extends AbstractIT {

    // for debugging integration tests
    public static void main(String[] args) {
        var t = new SubmitIT();
        try {
            t.aroundTest();

            t.testSubmit();
        } finally {
            t.aroundTest();
        }
    }

    

    @BeforeEach 
    @AfterEach
    public void aroundTest() {
        var detectQueueUrl = sqsProps.getDetectQueueUrl();
        clearQueue(detectQueueUrl);

        var alertQueueUrl = sqsProps.getAlertQueueUrl();;
        clearQueue(alertQueueUrl);
    }

    @Test
    public void testSubmit() {
        // first, sends a non-fraud request
        var nonFraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999)
                    .memo("N/A")
                    .build();
        var nonFraudEntity = appClient.submit(nonFraudReq);
        dump("non-fraud: ", nonFraudEntity);

        // no alert
        assertNull(pollAlert());

        // second, sends a fraud request
        var fraudReq = DetectionReq.builder()
                    .accountId("integration-test-account-1")
                    .amount(999 * 100000)
                    .memo("N/A")
                    .build();
        var fraudEntity = appClient.submit(fraudReq);
        dump("fraud: ", fraudEntity);

        // got alert
        assertNotNull(pollAlert());
    }

}
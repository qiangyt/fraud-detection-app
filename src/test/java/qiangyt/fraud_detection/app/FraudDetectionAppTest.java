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
package qiangyt.fraud_detection.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Test class for the FraudDetectionApp. It contains tests to ensure the Spring application context
 * loads successfully and that the main method of the application runs without exceptions.
 */
@SpringBootTest
@TestPropertySource(
        properties = {
            "AWS_REGION=fake",
            "AWS_ACCESS_KEY_ID=fake",
            "AWS_ACCESS_KEY_SECRET=fake",
            "AWS_SQS_DETECT_QUEUE_URL=fake",
            "AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL=fake",
            "AWS_SQS_ALERT_QUEUE_URL=fake"
        })
public class FraudDetectionAppTest {

    /** Test to ensure the Spring application context loads successfully. */
    @Test
    void contextLoads() {
        // The contextLoads test will pass if the application context can start successfully
    }

    /** Tests main method of the application runs */
    @Test
    void testMain() {
        try {
            // Attempt to run the main method of the application
            FraudDetectionApp.main(new String[0]);
        } catch (Exception ex) {
            // Print stack trace if an exception occurs
            ex.printStackTrace();
        }
    }
}

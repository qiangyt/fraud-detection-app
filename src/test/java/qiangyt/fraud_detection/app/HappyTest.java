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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.app.transaction.FraudDetector;
import qiangyt.fraud_detection.sdk.model.Transaction;

public class HappyTest {

    @Test
    public void testFraudulentTransaction() {
        Transaction transaction =
                new Transaction(); // "txn1", "account123", 20000, "2024-12-05", "credit");
        FraudDetector fraudDetector = new FraudDetector();
        assertTrue(fraudDetector.isFraudulent(transaction));
    }
}

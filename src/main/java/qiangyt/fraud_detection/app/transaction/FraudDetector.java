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
package qiangyt.fraud_detection.app.transaction;

import java.util.Set;
import qiangyt.fraud_detection.sdk.model.Transaction;

/*
 * A simple rule-based detection system
 */
public class FraudDetector {

    private static final double MAX_TRANSACTION_AMOUNT = 10000.0;

    private static final Set<String> SUSPICIOUS_ACCOUNTS = Set.of("account123", "account456");

    public boolean isFraudulent(Transaction transaction) {
        // rule 1: If the transaction amount exceeds a threshold, flag it as potentially fraudulent.
        if (transaction.getAmount() > MAX_TRANSACTION_AMOUNT) {
            return true;
        }

        // rule 2: If the transaction originates from a suspicious account, flag it as fraudulent.
        if (SUSPICIOUS_ACCOUNTS.contains(transaction.getAccountId())) {
            return true;
        }

        return false;
    }
}

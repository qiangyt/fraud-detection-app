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
package qiangyt.fraud_detection.sdk;

public enum FraudCategory {
    NONE(false, "none"),
    BIG_AMOUNT(true, "the transaction amount exceeds a threshold"),
    SUSPICIOUS_ACCOUNT(true, "the transaction originates from a suspicious account");

    public final boolean yes;
    public final String message;

    private FraudCategory(boolean yes, String message) {
        this.yes = yes;
        this.message = message;
    }
}

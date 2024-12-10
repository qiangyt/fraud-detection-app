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

import java.util.Date;

/** Represents a detection request entity containing details about a fraud detection request. */
@lombok.Getter
@lombok.Setter
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
public class DetectionReqEntity {

    /** The account ID associated with the detection request. */
    String accountId;

    /** The amount involved in the detection request. */
    int amount;

    /** A memo or note associated with the detection request. */
    String memo;

    /** The unique identifier for the detection request. */
    String id;

    /** The date and time when the detection request was received. */
    Date receivedAt;
}

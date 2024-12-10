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
import qiangyt.fraud_detection.framework.misc.UuidHelper;

@lombok.Getter
@lombok.Setter
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
/** Represents the result of a fraud detection process. */
public class DetectionResult {

    /** Unique identifier for the detection result. */
    String id;

    /** The entity that was analyzed for fraud. */
    DetectionReqEntity entity;

    /** Indicates whether the entity is fraudulent. */
    boolean fraudulent;

    /** The category of fraud detected. */
    FraudCategory category;

    /** A message providing additional information about the detection result. */
    String message;

    /** The date and time when the fraud was detected. */
    Date detectedAt;

    /**
     * Creates a DetectionResult from the given entity and fraud category.
     *
     * @param entity the entity that was analyzed for fraud
     * @param category the category of fraud detected
     * @return a new DetectionResult instance
     */
    public static DetectionResult from(DetectionReqEntity entity, FraudCategory category) {
        return DetectionResult.builder()
                .id(UuidHelper.shortUuid())
                .entity(entity)
                .fraudulent(category.yes)
                .category(category)
                .message(category.message)
                .detectedAt(new Date())
                .build();
    }
}

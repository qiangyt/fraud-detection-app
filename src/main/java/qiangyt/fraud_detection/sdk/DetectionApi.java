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

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** Interface for the fraud detection API. */
@Validated
public interface DetectionApi {

    /**
     * Submits a detection request.
     *
     * @param req the detection request
     * @return the detection request entity
     */
    @NotNull
    DetectionReqEntity submit(@NotNull DetectionReq req);

    /**
     * Detects fraud based on the provided detection request entity. This method is intended for
     * testing purposes only.
     *
     * @param entity the detection request entity
     * @return the detection result
     */
    @NotNull
    DetectionResult detect(@NotNull DetectionReqEntity entity);
}

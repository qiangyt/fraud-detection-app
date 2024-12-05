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
package qiangyt.fraud_detection.framework.errs;

import java.time.Instant;

import qiangyt.fraud_detection.framework.json.JacksonHelper;

@lombok.Getter
@lombok.Setter
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
public class ErrorResponse {

    Instant timestamp;

    int status;

    String error;

    ErrorCode code;

    String message;

    Object[] params;

    String path;

    @Override
    public String toString() {
        return JacksonHelper.pretty(this);
    }
}

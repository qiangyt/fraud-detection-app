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
/** Represents an error response with details about the error. */
public class ErrorResponse {

    /** The timestamp when the error occurred. */
    Instant timestamp;

    /** The HTTP status code of the error. */
    int status;

    /** A short description of the error. */
    String error;

    /** The specific error code. */
    ErrorCode code;

    /** A detailed message about the error. */
    String message;

    /** Additional parameters related to the error. */
    Object[] params;

    /** The path where the error occurred. */
    String path;

    /**
     * Converts the error response to a pretty-printed JSON string.
     *
     * @return the JSON string representation of the error response.
     */
    @Override
    public String toString() {
        return JacksonHelper.pretty(this);
    }
}

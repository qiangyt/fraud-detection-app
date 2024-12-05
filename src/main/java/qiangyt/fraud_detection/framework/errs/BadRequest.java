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

import org.springframework.http.HttpStatus;

/** HTTP BAD_REQUEST */
public class BadRequest extends BaseError {

    public BadRequest(ErrorCode code, String messageFormat, Object... params) {
        super(HttpStatus.BAD_REQUEST, code, messageFormat, params);
    }

    public BadRequest(ErrorCode code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }

    public BadRequest(ErrorCode code) {
        super(HttpStatus.BAD_REQUEST, code);
    }

    public BadRequest(ErrorCode code, Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.BAD_REQUEST, code, cause, messageFormat, params);
    }

    public BadRequest(ErrorCode code, Throwable cause, String message) {
        super(HttpStatus.BAD_REQUEST, code, cause, message);
    }

    public BadRequest(ErrorCode code, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, code, cause);
    }
}

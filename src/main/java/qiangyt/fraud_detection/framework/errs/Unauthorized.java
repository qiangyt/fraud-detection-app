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

/** HTTP UNAUTHORIZED */
public class Unauthorized extends BaseError {

    public Unauthorized(String messageFormat, Object... params) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NONE, messageFormat, params);
    }

    public Unauthorized(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NONE, message);
    }

    public Unauthorized(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NONE, cause, messageFormat, params);
    }

    public Unauthorized(Throwable cause, String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NONE, cause, message);
    }

    public Unauthorized(Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NONE, cause);
    }
}

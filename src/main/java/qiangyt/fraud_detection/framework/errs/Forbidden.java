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

/** HTTP FORBIDDEN */
public class Forbidden extends BaseError {

    public Forbidden(ErrorCode code, String messageFormat, Object... params) {
        super(HttpStatus.FORBIDDEN, code, messageFormat, params);
    }

    public Forbidden(ErrorCode code, String message) {
        super(HttpStatus.FORBIDDEN, code, message);
    }

    public Forbidden(ErrorCode code) {
        super(HttpStatus.FORBIDDEN, code);
    }

    public Forbidden(ErrorCode code, Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.FORBIDDEN, code, cause, messageFormat, params);
    }

    public Forbidden(ErrorCode code, Throwable cause, String message) {
        super(HttpStatus.FORBIDDEN, code, cause, message);
    }

    public Forbidden(ErrorCode code, Throwable cause) {
        super(HttpStatus.FORBIDDEN, code, cause);
    }
}

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

/** HTTP NOT_ACCEPTABLE */
public class NotAcceptable extends BaseError {

    public NotAcceptable(String messageFormat, Object... params) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, messageFormat, params);
    }

    public NotAcceptable(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, message);
    }

    public NotAcceptable() {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE);
    }

    public NotAcceptable(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause, messageFormat, params);
    }

    public NotAcceptable(Throwable cause, String message) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause, message);
    }

    public NotAcceptable(Throwable cause) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause);
    }
}

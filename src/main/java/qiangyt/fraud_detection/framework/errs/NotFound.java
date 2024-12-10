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

/** HTTP NOT_FOUND */
/** Exception representing an HTTP 404 Not Found error. */
public class NotFound extends BaseError {

    /**
     * Constructs a new NotFound exception with the specified error code, message format, and
     * parameters.
     *
     * @param code the error code
     * @param messageFormat the message format
     * @param params the parameters for the message format
     */
    public NotFound(ErrorCode code, String messageFormat, Object... params) {
        super(HttpStatus.NOT_FOUND, code, messageFormat, params);
    }

    /**
     * Constructs a new NotFound exception with the specified error code and message.
     *
     * @param code the error code
     * @param message the message
     */
    public NotFound(ErrorCode code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }

    /**
     * Constructs a new NotFound exception with the specified error code.
     *
     * @param code the error code
     */
    public NotFound(ErrorCode code) {
        super(HttpStatus.NOT_FOUND, code);
    }

    /**
     * Constructs a new NotFound exception with the specified error code, cause, message format, and
     * parameters.
     *
     * @param code the error code
     * @param cause the cause of the exception
     * @param messageFormat the message format
     * @param params the parameters for the message format
     */
    public NotFound(ErrorCode code, Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.NOT_FOUND, code, cause, messageFormat, params);
    }

    /**
     * Constructs a new NotFound exception with the specified error code, cause, and message.
     *
     * @param code the error code
     * @param cause the cause of the exception
     * @param message the message
     */
    public NotFound(ErrorCode code, Throwable cause, String message) {
        super(HttpStatus.NOT_FOUND, code, cause, message);
    }

    /**
     * Constructs a new NotFound exception with the specified error code and cause.
     *
     * @param code the error code
     * @param cause the cause of the exception
     */
    public NotFound(ErrorCode code, Throwable cause) {
        super(HttpStatus.NOT_FOUND, code, cause);
    }
}

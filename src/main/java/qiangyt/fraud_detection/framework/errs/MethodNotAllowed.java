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

/** HTTP METHOD_NOT_ALLOWED */
public class MethodNotAllowed extends BaseError {

    /**
     * Constructs a new MethodNotAllowed exception with the specified detail message format and
     * parameters.
     *
     * @param messageFormat the detail message format
     * @param params the parameters for the message format
     */
    public MethodNotAllowed(String messageFormat, Object... params) {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE, messageFormat, params);
    }

    /**
     * Constructs a new MethodNotAllowed exception with the specified detail message.
     *
     * @param message the detail message
     */
    public MethodNotAllowed(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE, message);
    }

    /** Constructs a new MethodNotAllowed exception with no detail message. */
    public MethodNotAllowed() {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE);
    }

    /**
     * Constructs a new MethodNotAllowed exception with the specified cause, detail message format,
     * and parameters.
     *
     * @param cause the cause of the exception
     * @param messageFormat the detail message format
     * @param params the parameters for the message format
     */
    public MethodNotAllowed(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE, cause, messageFormat, params);
    }

    /**
     * Constructs a new MethodNotAllowed exception with the specified cause and detail message.
     *
     * @param cause the cause of the exception
     * @param message the detail message
     */
    public MethodNotAllowed(Throwable cause, String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE, cause, message);
    }

    /**
     * Constructs a new MethodNotAllowed exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public MethodNotAllowed(Throwable cause) {
        super(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.NONE, cause);
    }
}

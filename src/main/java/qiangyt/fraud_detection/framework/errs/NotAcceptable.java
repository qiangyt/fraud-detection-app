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
/** Represents an HTTP 406 Not Acceptable error. */
public class NotAcceptable extends BaseError {

    /**
     * Constructs a new NotAcceptable error with a formatted message.
     *
     * @param messageFormat the message format
     * @param params the parameters for the message format
     */
    public NotAcceptable(String messageFormat, Object... params) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, messageFormat, params);
    }

    /**
     * Constructs a new NotAcceptable error with a message.
     *
     * @param message the error message
     */
    public NotAcceptable(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, message);
    }

    /** Constructs a new NotAcceptable error with no message. */
    public NotAcceptable() {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE);
    }

    /**
     * Constructs a new NotAcceptable error with a cause and a formatted message.
     *
     * @param cause the cause of the error
     * @param messageFormat the message format
     * @param params the parameters for the message format
     */
    public NotAcceptable(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause, messageFormat, params);
    }

    /**
     * Constructs a new NotAcceptable error with a cause and a message.
     *
     * @param cause the cause of the error
     * @param message the error message
     */
    public NotAcceptable(Throwable cause, String message) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause, message);
    }

    /**
     * Constructs a new NotAcceptable error with a cause.
     *
     * @param cause the cause of the error
     */
    public NotAcceptable(Throwable cause) {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorCode.NONE, cause);
    }
}

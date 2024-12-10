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

/**
 * Exception representing HTTP 415 Unsupported Media Type error. This exception is thrown when the
 * server refuses to accept the request because the payload format is in an unsupported format.
 */
public class UnsupportedMediaType extends BaseError {

    /**
     * Constructs a new UnsupportedMediaType exception with the specified detail message format and
     * parameters.
     *
     * @param messageFormat the detail message format.
     * @param params the parameters for the message format.
     */
    public UnsupportedMediaType(String messageFormat, Object... params) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE, messageFormat, params);
    }

    /**
     * Constructs a new UnsupportedMediaType exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public UnsupportedMediaType(String message) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE, message);
    }

    /** Constructs a new UnsupportedMediaType exception with no detail message. */
    public UnsupportedMediaType() {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE);
    }

    /**
     * Constructs a new UnsupportedMediaType exception with the specified cause, detail message
     * format, and parameters.
     *
     * @param cause the cause of the exception.
     * @param messageFormat the detail message format.
     * @param params the parameters for the message format.
     */
    public UnsupportedMediaType(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE, cause, messageFormat, params);
    }

    /**
     * Constructs a new UnsupportedMediaType exception with the specified cause and detail message.
     *
     * @param cause the cause of the exception.
     * @param message the detail message.
     */
    public UnsupportedMediaType(Throwable cause, String message) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE, cause, message);
    }

    /**
     * Constructs a new UnsupportedMediaType exception with the specified cause.
     *
     * @param cause the cause of the exception.
     */
    public UnsupportedMediaType(Throwable cause) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.NONE, cause);
    }
}

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
import org.springframework.http.HttpStatus;

/**
 * Base class for all exception classes. Generally not used directly; typically, one of its
 * subclasses is used instead.
 */
@lombok.Getter
public abstract class BaseError extends RuntimeException {

    public static final Object[] NO_PARAMS = new Object[0];

    /** Error status */
    final HttpStatus status;

    /** Error code */
    final ErrorCode code;

    /** Error message parameters */
    final Object[] params;

    /**
     * Exception thrown when there is no cascading exception; allows specifying message format and
     * message parameters.
     *
     * @param status Error status
     * @param code Error code
     * @param messageFormat Error message format
     * @param params Error message parameters
     * @see String#format
     */
    public BaseError(
            HttpStatus status, ErrorCode code, String messageFormat, Object... params) {
        super(String.format(messageFormat, params));

        this.status = status;
        this.code = code == null ? ErrorCode.NONE : code;
        this.params = params == null ? NO_PARAMS : params;
    }

    /**
     * Exception thrown when there is no cascading exception; only simple message text can be
     * specified.
     *
     * @param status Error status
     * @param code Error code
     * @param message Error message
     */
    public BaseError(HttpStatus status, ErrorCode code, String message) {
        super(message);

        this.status = status;
        this.code = code == null ? ErrorCode.NONE : code;
        this.params = NO_PARAMS;
    }

    /**
     * Exception thrown when there is no cascading exception; message text not needed, as the error
     * message represented by the error code is used directly.
     *
     * @param status Error status
     * @param code Error code
     */
    public BaseError(HttpStatus status, ErrorCode code) {
        this(status, code, status.getReasonPhrase());
    }

    /**
     * Exception thrown when there is a cascading exception; allows specifying message format and
     * error message parameters.
     *
     * @param cause the cascading exception
     * @param status Error status
     * @param code Error code
     * @param messageFormat Error message format
     * @param params Error message parameters
     * @see String#format
     */
    public BaseError(
            HttpStatus status,
            ErrorCode code,
            Throwable cause,
            String messageFormat,
            Object... params) {
        super(String.format(messageFormat, params), cause);

        this.status = status;
        this.code = code == null ? ErrorCode.NONE : code;
        this.params = params == null ? NO_PARAMS : params;
    }

    /**
     * Exception thrown when there is a cascading exception; only simple message text can be passed.
     *
     * @param cause the cascading exception
     * @param status Error status
     * @param code Error code
     * @param message Error message
     */
    public BaseError(HttpStatus status, ErrorCode code, Throwable cause, String message) {
        super(message, cause);

        this.status = status;
        this.code = code == null ? ErrorCode.NONE : code;
        this.params = NO_PARAMS;
    }

    /**
     * Exception thrown when there is a cascading exception; message text not needed, as the error
     * message represented by the error code is used directly.
     *
     * @param cause the cascading exception
     * @param status Error status
     * @param code Error code
     */
    public BaseError(HttpStatus status, ErrorCode code, Throwable cause) {
        this(status, code, cause, status.getReasonPhrase());
    }

    /**
     * Export to a Map. Cascading exceptions are not included. For RESTful APIs, this map serves as
     * the HTTP response body.
     */
    public ErrorResponse toResponse(String path) {
        return ErrorResponse.builder()
                .path(path == null ? "" : path)
                .timestamp(Instant.now())
                .status(getStatus().value())
                .error(getStatus().getReasonPhrase())
                .code(getCode())
                .message(getMessage())
                .params(getParams())
                .build();
    }
}

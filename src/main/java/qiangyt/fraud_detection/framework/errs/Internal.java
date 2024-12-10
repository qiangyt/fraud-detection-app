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
 * Represents an HTTP 500 Internal Server Error. This class extends {@link BaseError} to provide
 * additional context for internal server errors.
 */
@lombok.Getter
@lombok.Setter
public class Internal extends BaseError {

    Long id;

    /**
     * Constructs a new Internal error with the specified message format and parameters.
     *
     * @param messageFormat the message format string
     * @param params the parameters to be formatted into the message
     */
    public Internal(String messageFormat, Object... params) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE, messageFormat, params);
    }

    /**
     * Constructs a new Internal error with the specified message.
     *
     * @param message the error message
     */
    public Internal(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE, message);
    }

    /**
     * Constructs a new Internal error with the specified cause, message format, and parameters.
     *
     * @param cause the cause of the error
     * @param messageFormat the message format string
     * @param params the parameters to be formatted into the message
     */
    public Internal(Throwable cause, String messageFormat, Object... params) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE, cause, messageFormat, params);
    }

    /**
     * Constructs a new Internal error with the specified cause and message.
     *
     * @param cause the cause of the error
     * @param message the error message
     */
    public Internal(Throwable cause, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE, cause, message);
    }

    /**
     * Constructs a new Internal error with the specified cause.
     *
     * @param cause the cause of the error
     */
    public Internal(Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.NONE, cause);
    }

    /**
     * Converts this error to an {@link ErrorResponse} object.
     *
     * @param path the request path that caused the error
     * @return the error response
     */
    @Override
    public ErrorResponse toResponse(String path) {
        return ErrorResponse.builder()
                .path(path == null ? "" : path)
                .timestamp(Instant.now())
                .status(getStatus().value())
                .error(getStatus().getReasonPhrase())
                .code(getCode())
                .message("")
                .params(NO_PARAMS)
                .build();
    }
}

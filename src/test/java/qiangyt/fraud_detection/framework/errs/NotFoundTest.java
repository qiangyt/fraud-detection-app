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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/** Unit tests for the {@link NotFound} exception class. */
class NotFoundTest {

    /** Tests the NotFound exception with a formatted message. */
    @Test
    void testNotFoundWithMessageFormat() {
        // Create NotFound exception with formatted message
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, "Resource %s not found", "User");

        // Verify the status, code, and message
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource User not found", ex.getMessage());
    }

    /** Tests the NotFound exception with a simple message. */
    @Test
    void testNotFoundWithMessage() {
        // Create NotFound exception with simple message
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, "Resource not found");

        // Verify the status, code, and message
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource not found", ex.getMessage());
    }

    /** Tests the NotFound exception with only an error code. */
    @Test
    void testNotFoundWithCodeOnly() {
        // Create NotFound exception with only error code
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND);

        // Verify the status, code, and default message
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    /** Tests the NotFound exception with a cause and a formatted message. */
    @Test
    void testNotFoundWithCauseAndMessageFormat() {
        // Create a cause exception
        var cause = new RuntimeException("Underlying cause");

        // Create NotFound exception with cause and formatted message
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause, "Resource %s not found", "User");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource User not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the NotFound exception with a cause and a simple message. */
    @Test
    void testNotFoundWithCauseAndMessage() {
        // Create a cause exception
        var cause = new RuntimeException("Underlying cause");

        // Create NotFound exception with cause and simple message
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause, "Resource not found");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the NotFound exception with only a cause. */
    @Test
    void testNotFoundWithCauseOnly() {
        // Create a cause exception
        var cause = new RuntimeException("Underlying cause");

        // Create NotFound exception with only cause
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause);

        // Verify the status, code, default message, and cause
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

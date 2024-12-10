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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/** Unit tests for the {@link Internal} class. */
public class InternalTest {

    /** Tests the constructor of {@link Internal} with a formatted message. */
    @Test
    public void testInternalWithMessageFormat() {
        // Create an Internal exception with a formatted message
        var ex = new Internal("Internal error: %s", "error1");

        // Verify the status, code, and message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
    }

    /** Tests the constructor of {@link Internal} with a simple message. */
    @Test
    public void testInternalWithMessage() {
        // Create an Internal exception with a simple message
        var ex = new Internal("Internal error");

        // Verify the status, code, and message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
    }

    /** Tests the constructor of {@link Internal} with a cause and a formatted message. */
    @Test
    public void testInternalWithCauseAndMessageFormat() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create an Internal exception with a cause and a formatted message
        var ex = new Internal(cause, "Internal error: %s", "error1");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the constructor of {@link Internal} with a cause and a simple message. */
    @Test
    public void testInternalWithCauseAndMessage() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create an Internal exception with a cause and a simple message
        var ex = new Internal(cause, "Internal error");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the constructor of {@link Internal} with only a cause. */
    @Test
    public void testInternalWithCause() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create an Internal exception with only a cause
        var ex = new Internal(cause);

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal Server Error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

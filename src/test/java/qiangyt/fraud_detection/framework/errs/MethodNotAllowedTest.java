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

/** Unit tests for the {@link MethodNotAllowed} exception. */
public class MethodNotAllowedTest {

    /** Tests the {@link MethodNotAllowed} constructor with a formatted message. */
    @Test
    public void testMethodNotAllowedWithMessageFormat() {
        // Create exception with formatted message
        var ex = new MethodNotAllowed("Invalid method: %s", "POST");
        // Verify status, code, and message
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
    }

    /** Tests the {@link MethodNotAllowed} constructor with a simple message. */
    @Test
    public void testMethodNotAllowedWithMessage() {
        // Create exception with simple message
        var ex = new MethodNotAllowed("Method not allowed");
        // Verify status, code, and message
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
    }

    /** Tests the {@link MethodNotAllowed} constructor with a cause and formatted message. */
    @Test
    public void testMethodNotAllowedWithCauseAndMessageFormat() {
        // Create cause exception
        var cause = new RuntimeException("Root cause");
        // Create exception with cause and formatted message
        var ex = new MethodNotAllowed(cause, "Invalid method: %s", "POST");
        // Verify status, code, message, and cause
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the {@link MethodNotAllowed} constructor with a cause and simple message. */
    @Test
    public void testMethodNotAllowedWithCauseAndMessage() {
        // Create cause exception
        var cause = new RuntimeException("Root cause");
        // Create exception with cause and simple message
        var ex = new MethodNotAllowed(cause, "Method not allowed");
        // Verify status, code, message, and cause
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the {@link MethodNotAllowed} constructor with only a cause. */
    @Test
    public void testMethodNotAllowedWithCause() {
        // Create cause exception
        var cause = new RuntimeException("Root cause");
        // Create exception with cause
        var ex = new MethodNotAllowed(cause);
        // Verify status, code, message, and cause
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method Not Allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

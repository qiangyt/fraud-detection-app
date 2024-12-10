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

/** Unit tests for the {@link NotAcceptable} exception class. */
public class NotAcceptableTest {

    /** Test the NotAcceptable exception with a formatted message. */
    @Test
    public void testNotAcceptableWithMessageFormat() {
        // Create exception with formatted message
        var ex = new NotAcceptable("Invalid parameter: %s", "param1");

        // Verify the status, code, and message
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
    }

    /** Test the NotAcceptable exception with a simple message. */
    @Test
    public void testNotAcceptableWithMessage() {
        // Create exception with simple message
        var ex = new NotAcceptable("Invalid request");

        // Verify the status, code, and message
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
    }

    /** Test the NotAcceptable exception with a cause and formatted message. */
    @Test
    public void testNotAcceptableWithCauseAndMessageFormat() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create exception with cause and formatted message
        var ex = new NotAcceptable(cause, "Invalid parameter: %s", "param1");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Test the NotAcceptable exception with a cause and simple message. */
    @Test
    public void testNotAcceptableWithCauseAndMessage() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create exception with cause and simple message
        var ex = new NotAcceptable(cause, "Invalid request");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Test the NotAcceptable exception with only a cause. */
    @Test
    public void testNotAcceptableWithCause() {
        // Create a cause exception
        var cause = new RuntimeException("Root cause");

        // Create exception with cause
        var ex = new NotAcceptable(cause);

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Not Acceptable", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

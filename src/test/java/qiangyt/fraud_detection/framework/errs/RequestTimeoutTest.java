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

/** Unit tests for the {@link RequestTimeout} class. */
class RequestTimeoutTest {

    /** Tests the constructor with a formatted message. */
    @Test
    void testRequestTimeoutWithMessageFormat() {
        // Create exception with formatted message
        var ex = new RequestTimeout("Timeout after %d seconds", 30);

        // Verify the status, code, and message
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
    }

    /** Tests the constructor with a simple message. */
    @Test
    void testRequestTimeoutWithMessage() {
        // Create exception with simple message
        var ex = new RequestTimeout("Timeout occurred");

        // Verify the status, code, and message
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
    }

    /** Tests the constructor without a message. */
    @Test
    void testRequestTimeoutWithoutMessage() {
        // Create exception without message
        var ex = new RequestTimeout();

        // Verify the status, code, and default message
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
    }

    /** Tests the constructor with a cause and a formatted message. */
    @Test
    void testRequestTimeoutWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Underlying cause");

        // Create exception with cause and formatted message
        var ex = new RequestTimeout(cause, "Timeout after %d seconds", 30);

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the constructor with a cause and a simple message. */
    @Test
    void testRequestTimeoutWithCauseAndMessage() {
        var cause = new RuntimeException("Underlying cause");

        // Create exception with cause and simple message
        var ex = new RequestTimeout(cause, "Timeout occurred");

        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the constructor with only a cause. */
    @Test
    void testRequestTimeoutWithCauseOnly() {
        var cause = new RuntimeException("Underlying cause");

        // Create exception with only cause
        var ex = new RequestTimeout(cause);

        // Verify the status, code, default message, and cause
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

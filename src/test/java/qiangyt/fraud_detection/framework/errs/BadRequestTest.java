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

/** Unit tests for the {@link BadRequest} class. */
public class BadRequestTest {

    /** Tests the BadRequest constructor with a formatted message. */
    @Test
    public void testBadRequestWithMessageFormat() {
        // Create BadRequest exception with formatted message
        var ex = new BadRequest(ErrorCode.NONE, "Invalid parameter: %s", "param1");
        // Verify the status, code, and message
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
    }

    /** Tests the BadRequest constructor with a simple message. */
    @Test
    public void testBadRequestWithMessage() {
        // Create BadRequest exception with simple message
        var ex = new BadRequest(ErrorCode.NONE, "Invalid request");
        // Verify the status, code, and message
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
    }

    /** Tests the BadRequest constructor with a cause and formatted message. */
    @Test
    public void testBadRequestWithCauseAndMessageFormat() {
        // Create a root cause exception
        var cause = new RuntimeException("Root cause");
        // Create BadRequest exception with cause and formatted message
        var ex = new BadRequest(ErrorCode.NONE, cause, "Invalid parameter: %s", "param1");
        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the BadRequest constructor with a cause and simple message. */
    @Test
    public void testBadRequestWithCauseAndMessage() {
        // Create a root cause exception
        var cause = new RuntimeException("Root cause");
        // Create BadRequest exception with cause and simple message
        var ex = new BadRequest(ErrorCode.NONE, cause, "Invalid request");
        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    /** Tests the BadRequest constructor with only a cause. */
    @Test
    public void testBadRequestWithCause() {
        // Create a root cause exception
        var cause = new RuntimeException("Root cause");
        // Create BadRequest exception with only cause
        var ex = new BadRequest(ErrorCode.NONE, cause);
        // Verify the status, code, message, and cause
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Bad Request", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

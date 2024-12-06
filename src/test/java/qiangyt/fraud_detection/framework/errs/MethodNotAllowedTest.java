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

public class MethodNotAllowedTest {

    @Test
    public void testMethodNotAllowedWithMessageFormat() {
        var ex = new MethodNotAllowed("Invalid method: %s", "POST");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
    }

    @Test
    public void testMethodNotAllowedWithMessage() {
        var ex = new MethodNotAllowed("Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
    }

    @Test
    public void testMethodNotAllowedWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause, "Invalid method: %s", "POST");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testMethodNotAllowedWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause, "Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testMethodNotAllowedWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method Not Allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

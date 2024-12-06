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

public class InternalTest {

    @Test
    public void testInternalWithMessageFormat() {
        var ex = new Internal("Internal error: %s", "error1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
    }

    @Test
    public void testInternalWithMessage() {
        var ex = new Internal("Internal error");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
    }

    @Test
    public void testInternalWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause, "Internal error: %s", "error1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testInternalWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause, "Internal error");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testInternalWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal Server Error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

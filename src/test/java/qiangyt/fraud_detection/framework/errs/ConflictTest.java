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

public class ConflictTest {

    @Test
    public void testConflictWithMessageFormat() {
        var ex = new Conflict("Resource conflict: %s", "resource1");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict: resource1", ex.getMessage());
    }

    @Test
    public void testConflictWithMessage() {
        var ex = new Conflict("Resource conflict");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict", ex.getMessage());
    }

    @Test
    public void testConflictWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause, "Resource conflict: %s", "resource1");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict: resource1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConflictWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause, "Resource conflict");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConflictWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause);
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

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

public class ForbiddenTest {

    @Test
    public void testForbiddenWithMessageFormat() {
        var ex = new Forbidden(ErrorCode.NONE, "Access denied: %s", "resource1");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied: resource1", ex.getMessage());
    }

    @Test
    public void testForbiddenWithMessage() {
        var ex = new Forbidden(ErrorCode.NONE, "Access denied");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied", ex.getMessage());
    }

    @Test
    public void testForbiddenWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause, "Access denied: %s", "resource1");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied: resource1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testForbiddenWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause, "Access denied");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testForbiddenWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause);
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Forbidden", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

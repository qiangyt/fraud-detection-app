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

public class NotAcceptableTest {

    @Test
    public void testNotAcceptableWithMessageFormat() {
        var ex = new NotAcceptable("Invalid parameter: %s", "param1");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
    }

    @Test
    public void testNotAcceptableWithMessage() {
        var ex = new NotAcceptable("Invalid request");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
    }

    @Test
    public void testNotAcceptableWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new NotAcceptable(cause, "Invalid parameter: %s", "param1");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid parameter: param1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testNotAcceptableWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new NotAcceptable(cause, "Invalid request");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid request", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testNotAcceptableWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new NotAcceptable(cause);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Not Acceptable", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

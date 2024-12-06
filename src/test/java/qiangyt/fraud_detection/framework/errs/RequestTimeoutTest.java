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

class RequestTimeoutTest {

    @Test
    void testRequestTimeoutWithMessageFormat() {
        var ex = new RequestTimeout("Timeout after %d seconds", 30);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithMessage() {
        var ex = new RequestTimeout("Timeout occurred");
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithoutMessage() {
        var ex = new RequestTimeout();
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause, "Timeout after %d seconds", 30);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testRequestTimeoutWithCauseAndMessage() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause, "Timeout occurred");
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testRequestTimeoutWithCauseOnly() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

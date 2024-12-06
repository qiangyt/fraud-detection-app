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

class NotFoundTest {

    @Test
    void testNotFoundWithMessageFormat() {
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, "Resource %s not found", "User");
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource User not found", ex.getMessage());
    }

    @Test
    void testNotFoundWithMessage() {
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, "Resource not found");
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource not found", ex.getMessage());
    }

    @Test
    void testNotFoundWithCodeOnly() {
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @Test
    void testNotFoundWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause, "Resource %s not found", "User");
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource User not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testNotFoundWithCauseAndMessage() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause, "Resource not found");
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals("Resource not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testNotFoundWithCauseOnly() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new NotFound(ErrorCode.PATH_NOT_FOUND, cause);
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals(ErrorCode.PATH_NOT_FOUND, ex.getCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

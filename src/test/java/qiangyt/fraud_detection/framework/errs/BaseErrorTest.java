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

public class BaseErrorTest {

    @Test
    public void testBaseErrorWithMessageFormatAndParams() {
        // Arrange
        var status = HttpStatus.BAD_REQUEST;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        var messageFormat = "Invalid input: %s";
        Object[] params = {"username"};

        // Act
        var error = new BaseError(status, code, messageFormat, params) {};

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals("Invalid input: username", error.getMessage());
    }

    @Test
    public void testBaseErrorWithSimpleMessage() {
        // Arrange
        var status = HttpStatus.NOT_FOUND;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        String message = "Resource not found";

        // Act
        var error = new BaseError(status, code, message) {};
        ;

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
    }

    @Test
    public void testBaseErrorWithErrorCodeOnly() {
        // Arrange
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var code = ErrorCode.WRONG_DATA_FORMAT;

        // Act
        var error = new BaseError(status, code) {};
        ;

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals("Internal Server Error", error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndMessageFormat() {
        // Arrange
        var status = HttpStatus.CONFLICT;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        var messageFormat = "Duplicate entry: %s";
        Object[] params = {"email"};
        var cause = new RuntimeException("Cascading exception");

        // Act
        var error = new BaseError(status, code, cause, messageFormat, params) {};
        ;

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals("Duplicate entry: email", error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndSimpleMessage() {
        // Arrange
        var status = HttpStatus.FORBIDDEN;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        String message = "Access denied";
        var cause = new RuntimeException("Cascading exception");

        // Act
        var error = new BaseError(status, code, cause, message) {};
        ;

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals(message, error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndErrorCodeOnly() {
        // Arrange
        var status = HttpStatus.GONE;
        var code = ErrorCode.WRONG_DATA_FORMAT;

        var cause = new RuntimeException("Cascading exception");

        // Act
        var error = new BaseError(status, code, cause) {};
        ;

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals("Gone", error.getMessage());
    }

    @Test
    public void testToResponseMethod() {
        // Arrange
        var status = HttpStatus.UNAUTHORIZED;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        String message = "Unauthorized access";
        String path = "/api/resource";

        var error = new BaseError(status, code, message) {};
        ;

        // Act
        var response = error.toResponse(path);

        // Assert
        assertEquals(path, response.getPath());
        assertEquals(status.value(), response.getStatus());
        assertEquals(status.getReasonPhrase(), response.getError());
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testToResponseMethodWithNullPath() {
        // Arrange
        var status = HttpStatus.METHOD_NOT_ALLOWED;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        String message = "Method not allowed";
        String path = null;

        var error = new BaseError(status, code, message) {};
        ;

        // Act
        var response = error.toResponse(path);

        // Assert
        assertEquals("", response.getPath());
        assertEquals(status.value(), response.getStatus());
        assertEquals(status.getReasonPhrase(), response.getError());
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testToResponseMethodWithEmptyPath() {
        // Arrange
        var status = HttpStatus.NOT_ACCEPTABLE;
        var code = ErrorCode.WRONG_DATA_FORMAT;
        String message = "Not acceptable";
        String path = "";

        var error = new BaseError(status, code, message) {};
        ;

        // Act
        var response = error.toResponse(path);

        // Assert
        assertEquals("", response.getPath());
        assertEquals(status.value(), response.getStatus());
        assertEquals(status.getReasonPhrase(), response.getError());
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }
}

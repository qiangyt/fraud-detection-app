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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

class ErrorAdviceTest {

    @Test
    void handleError_Throwable() {
        // Arrange
        var ex = new RuntimeException("Test exception");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        var errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("", errorResponse.getMessage());
    }

    /*@Test
    void handleError_MethodArgumentNotValidException() {
        // Arrange
        var objErrors = new ArrayList<>();
        var fieldErr = mock(FieldError.class);
        when(fieldErr.getDefaultMessage()).thenReturn("Invalid value");
        when(fieldErr.getField()).thenReturn("fieldName");
        objErrors.add(fieldErr);

        var ex = new MethodArgumentNotValidException(null, objErrors);
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("fieldName 'Invalid value'", errorResponse.getMessage());
    }*/

    /* @Test
    void handleError_ConstraintViolationException() {
        // Arrange
        var violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn("propertyPath");
        when(violation.getMessage()).thenReturn("Constraint violated");

        var violations = new HashSet<>();
        violations.add(violation);

        var ex = new ConstraintViolationException(violations);
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("propertyPath: Constraint violated", errorResponse.getMessage());
    }*/

    /*@Test
    void handleError_BindException() {
        // Arrange
        var ex = new BindException(null, "Bind exception");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Bind exception", errorResponse.getMessage());
    }*/

    /*@Test
    void handleError_MissingServletRequestParameterException() {
        // Arrange
        var ex =
                new MissingServletRequestParameterException("paramName", "String");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("paramName is required", errorResponse.getMessage());
    }*/

    @Test
    void handleError_MissingServletRequestPartException() {
        // Arrange
        var ex = new MissingServletRequestPartException("partName");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        var errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Required part 'partName' is not present.", errorResponse.getMessage());
    }

    /*@Test
    void handleError_TypeMismatchException() {
        // Arrange
        var ex = new TypeMismatchException(null, String.class, "Type mismatch");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Type mismatch", errorResponse.getMessage());
    }*/

    /*@Test
    void handleError_JacksonException() {
        // Arrange
        var ex = new JacksonException(null) {};
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Jackson exception", errorResponse.getMessage());
    }*/

    @Test
    void handleError_IllegalArgumentException() {
        // Arrange
        var ex = new IllegalArgumentException("Illegal argument");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        var errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Illegal argument", errorResponse.getMessage());
    }

    /*@Test
    void handleError_HttpMediaTypeNotAcceptableException() {
        // Arrange
        var ex =
                new HttpMediaTypeNotAcceptableException(null, null);
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(NOT_ACCEPTABLE, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Not acceptable", errorResponse.getMessage());
    }*/

    @Test
    void handleError_HttpRequestMethodNotSupportedException() {
        // Arrange
        var ex = new HttpRequestMethodNotSupportedException("GET");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(METHOD_NOT_ALLOWED, response.getStatusCode());
        var errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Request method 'GET' is not supported", errorResponse.getMessage());
    }

    @Test
    void handleError_InternalServerError() {
        // Arrange
        var ex = new Exception("Internal server error");
        var req = mock(HttpServletRequest.class);
        var errorAdvice = new ErrorAdvice();

        // Act
        var response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        var errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("", errorResponse.getMessage());
    }
}

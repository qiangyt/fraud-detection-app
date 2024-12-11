package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

class ErrorAdviceTest {

    @Test
    void handleError_Throwable() {
        // Arrange
        Throwable ex = new RuntimeException("Test exception");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Test exception", errorResponse.getMessage());
    }

    @Test
    void handleError_MethodArgumentNotValidException() {
        // Arrange
        List<ObjectError> objErrors = new ArrayList<>();
        FieldError fieldErr = mock(FieldError.class);
        when(fieldErr.getDefaultMessage()).thenReturn("Invalid value");
        when(fieldErr.getField()).thenReturn("fieldName");
        objErrors.add(fieldErr);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, objErrors);
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("fieldName 'Invalid value'", errorResponse.getMessage());
    }

    @Test
    void handleError_ConstraintViolationException() {
        // Arrange
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn("propertyPath");
        when(violation.getMessage()).thenReturn("Constraint violated");

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);

        ConstraintViolationException ex = new ConstraintViolationException(violations);
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("propertyPath: Constraint violated", errorResponse.getMessage());
    }

    @Test
    void handleError_HttpMessageNotReadableException() {
        // Arrange
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid JSON");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Invalid JSON", errorResponse.getMessage());
    }

    @Test
    void handleError_BindException() {
        // Arrange
        BindException ex = new BindException(null, "Bind exception");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Bind exception", errorResponse.getMessage());
    }

    @Test
    void handleError_MissingServletRequestParameterException() {
        // Arrange
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("paramName", "String");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("paramName is required", errorResponse.getMessage());
    }

    @Test
    void handleError_MissingServletRequestPartException() {
        // Arrange
        MissingServletRequestPartException ex = new MissingServletRequestPartException("partName");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("partName is required", errorResponse.getMessage());
    }

    @Test
    void handleError_TypeMismatchException() {
        // Arrange
        TypeMismatchException ex = new TypeMismatchException(null, String.class, "Type mismatch");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Type mismatch", errorResponse.getMessage());
    }

    @Test
    void handleError_JacksonException() {
        // Arrange
        JacksonException ex = new JacksonException(null) {};
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Jackson exception", errorResponse.getMessage());
    }

    @Test
    void handleError_IllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Illegal argument", errorResponse.getMessage());
    }

    @Test
    void handleError_HttpMediaTypeNotAcceptableException() {
        // Arrange
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException(null, null);
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(NOT_ACCEPTABLE, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Not acceptable", errorResponse.getMessage());
    }

    @Test
    void handleError_HttpRequestMethodNotSupportedException() {
        // Arrange
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(METHOD_NOT_ALLOWED, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Method not allowed", errorResponse.getMessage());
    }

    @Test
    void handleError_InternalServerError() {
        // Arrange
        Exception ex = new Exception("Internal server error");
        HttpServletRequest req = mock(HttpServletRequest.class);
        ErrorAdvice errorAdvice = new ErrorAdvice();

        // Act
        ResponseEntity<ErrorResponse> response = errorAdvice.handleError(ex, req);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Internal server error", errorResponse.getMessage());
    }
}

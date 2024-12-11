package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BaseErrorTest {

    @Test
    public void testBaseErrorWithMessageFormatAndParams() {
        // Arrange
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorCode code = ErrorCode.INVALID_INPUT;
        String messageFormat = "Invalid input: %s";
        Object[] params = {"username"};

        // Act
        BaseError error = new BaseError(status, code, messageFormat, params);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals("Invalid input: username", error.getMessage());
    }

    @Test
    public void testBaseErrorWithSimpleMessage() {
        // Arrange
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorCode code = ErrorCode.RESOURCE_NOT_FOUND;
        String message = "Resource not found";

        // Act
        BaseError error = new BaseError(status, code, message);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals(message, error.getMessage());
    }

    @Test
    public void testBaseErrorWithErrorCodeOnly() {
        // Arrange
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorCode code = ErrorCode.SERVER_ERROR;

        // Act
        BaseError error = new BaseError(status, code);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertEquals("Internal Server Error", error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndMessageFormat() {
        // Arrange
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorCode code = ErrorCode.DUPLICATE_ENTRY;
        String messageFormat = "Duplicate entry: %s";
        Object[] params = {"email"};
        Exception cause = new RuntimeException("Cascading exception");

        // Act
        BaseError error = new BaseError(status, code, cause, messageFormat, params);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals("Duplicate entry: email", error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndSimpleMessage() {
        // Arrange
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorCode code = ErrorCode.ACCESS_DENIED;
        String message = "Access denied";
        Exception cause = new RuntimeException("Cascading exception");

        // Act
        BaseError error = new BaseError(status, code, cause, message);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals(message, error.getMessage());
    }

    @Test
    public void testBaseErrorWithCascadingExceptionAndErrorCodeOnly() {
        // Arrange
        HttpStatus status = HttpStatus.GONE;
        ErrorCode code = ErrorCode.RESOURCE_GONE;

        Exception cause = new RuntimeException("Cascading exception");

        // Act
        BaseError error = new BaseError(status, code, cause);

        // Assert
        assertEquals(status, error.getStatus());
        assertEquals(code, error.getCode());
        assertTrue(error.getCause() instanceof RuntimeException);
        assertEquals("Gone", error.getMessage());
    }

    @Test
    public void testToResponseMethod() {
        // Arrange
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorCode code = ErrorCode.UNAUTHORIZED_ACCESS;
        String message = "Unauthorized access";
        String path = "/api/resource";

        BaseError error = new BaseError(status, code, message);

        // Act
        ErrorResponse response = error.toResponse(path);

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
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorCode code = ErrorCode.METHOD_NOT_ALLOWED;
        String message = "Method not allowed";
        String path = null;

        BaseError error = new BaseError(status, code, message);

        // Act
        ErrorResponse response = error.toResponse(path);

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
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        ErrorCode code = ErrorCode.NOT_ACCEPTABLE;
        String message = "Not acceptable";
        String path = "";

        BaseError error = new BaseError(status, code, message);

        // Act
        ErrorResponse response = error.toResponse(path);

        // Assert
        assertEquals("", response.getPath());
        assertEquals(status.value(), response.getStatus());
        assertEquals(status.getReasonPhrase(), response.getError());
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }
}

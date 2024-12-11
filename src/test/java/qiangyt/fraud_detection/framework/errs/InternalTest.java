package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InternalTest {

    /**
     * Test the constructor with message format and parameters.
     */
    @Test
    public void testConstructorWithMessageFormatAndParameters() {
        // Arrange
        String messageFormat = "An error occurred: {0}";
        Object[] params = {"test"};

        // Act
        Internal internalError = new Internal(messageFormat, params);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalError.getStatus().value());
        assertEquals("NONE", internalError.getCode());
        assertEquals("An error occurred: test", internalError.getMessage());
    }

    /**
     * Test the constructor with message.
     */
    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "An error occurred";

        // Act
        Internal internalError = new Internal(message);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalError.getStatus().value());
        assertEquals("NONE", internalError.getCode());
        assertEquals("An error occurred", internalError.getMessage());
    }

    /**
     * Test the constructor with cause, message format, and parameters.
     */
    @Test
    public void testConstructorWithCauseMessageFormatAndParameters() {
        // Arrange
        Throwable cause = new RuntimeException("test cause");
        String messageFormat = "An error occurred: {0}";
        Object[] params = {"test"};

        // Act
        Internal internalError = new Internal(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalError.getStatus().value());
        assertEquals("NONE", internalError.getCode());
        assertEquals("An error occurred: test", internalError.getMessage());
        assertSame(cause, internalError.getCause());
    }

    /**
     * Test the constructor with cause and message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        // Arrange
        Throwable cause = new RuntimeException("test cause");
        String message = "An error occurred";

        // Act
        Internal internalError = new Internal(cause, message);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalError.getStatus().value());
        assertEquals("NONE", internalError.getCode());
        assertEquals(message, internalError.getMessage());
        assertSame(cause, internalError.getCause());
    }

    /**
     * Test the constructor with cause.
     */
    @Test
    public void testConstructorWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("test cause");

        // Act
        Internal internalError = new Internal(cause);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalError.getStatus().value());
        assertEquals("NONE", internalError.getCode());
        assertNull(internalError.getMessage());
        assertSame(cause, internalError.getCause());
    }

    /**
     * Test the toResponse method with valid parameters.
     */
    @Test
    public void testToResponseWithValidParameters() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "/test";

        // Act
        ErrorResponse response = internalError.toResponse(path);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", response.getError());
        assertEquals("NONE", response.getCode());
        assertEquals("", response.getMessage());
    }

    /**
     * Test the toResponse method with null path.
     */
    @Test
    public void testToResponseWithNullPath() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse(path));
    }

    /**
     * Test the toResponse method with empty path.
     */
    @Test
    public void testToResponseWithEmptyPath() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "";

        // Act
        ErrorResponse response = internalError.toResponse(path);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", response.getError());
        assertEquals("NONE", response.getCode());
        assertEquals("", response.getMessage());
    }

    /**
     * Test the toResponse method with null timestamp.
     */
    @Test
    public void testToResponseWithNullTimestamp() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        ErrorResponse.builder().timestamp(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse("/test"));
    }

    /**
     * Test the toResponse method with null status.
     */
    @Test
    public void testToResponseWithNullStatus() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        ErrorResponse.builder().status(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse("/test"));
    }

    /**
     * Test the toResponse method with null error.
     */
    @Test
    public void testToResponseWithNullError() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        ErrorResponse.builder().error(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse("/test"));
    }

    /**
     * Test the toResponse method with null code.
     */
    @Test
    public void testToResponseWithNullCode() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        ErrorResponse.builder().code(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse("/test"));
    }

    /**
     * Test the toResponse method with null message.
     */
    @Test
    public void testToResponseWithNullMessage() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        ErrorResponse.builder().message(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> internalError.toResponse("/test"));
    }

    /**
     * Test the toResponse method with custom status and error.
     */
    @Test
    public void testToResponseWithCustomStatusAndError() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "/test";
        int customStatus = 404;
        String customError = "Not Found";

        // Act
        ErrorResponse response = internalError.toResponse(path, customStatus, customError);

        // Assert
        assertNotNull(response);
        assertEquals(customStatus, response.getStatus());
        assertEquals(customError, response.getError());
        assertEquals("NONE", response.getCode());
        assertEquals("", response.getMessage());
    }

    /**
     * Test the toResponse method with custom status and error code.
     */
    @Test
    public void testToResponseWithCustomStatusAndErrorCode() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "/test";
        int customStatus = 404;
        String customError = "Not Found";
        String customCode = "ERR_404";

        // Act
        ErrorResponse response = internalError.toResponse(path, customStatus, customError, customCode);

        // Assert
        assertNotNull(response);
        assertEquals(customStatus, response.getStatus());
        assertEquals(customError, response.getError());
        assertEquals(customCode, response.getCode());
        assertEquals("", response.getMessage());
    }

    /**
     * Test the toResponse method with custom status and error message.
     */
    @Test
    public void testToResponseWithCustomStatusAndErrorMessage() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "/test";
        int customStatus = 404;
        String customError = "Not Found";
        String customMessage = "Resource not found";

        // Act
        ErrorResponse response = internalError.toResponse(path, customStatus, customError, null, customMessage);

        // Assert
        assertNotNull(response);
        assertEquals(customStatus, response.getStatus());
        assertEquals(customError, response.getError());
        assertEquals("NONE", response.getCode());
        assertEquals(customMessage, response.getMessage());
    }

    /**
     * Test the toResponse method with custom status and error code and message.
     */
    @Test
    public void testToResponseWithCustomStatusAndErrorCodeAndMessage() {
        // Arrange
        Internal internalError = new Internal("An error occurred");
        String path = "/test";
        int customStatus = 404;
        String customError = "Not Found";
        String customCode = "ERR_404";
        String customMessage = "Resource not found";

        // Act
        ErrorResponse response = internalError.toResponse(path, customStatus, customError, customCode, customMessage);

        // Assert
        assertNotNull(response);
        assertEquals(customStatus, response.getStatus());
        assertEquals(customError, response.getError());
        assertEquals(customCode, response.getCode());
        assertEquals(customMessage, response.getMessage());
    }
}

package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MethodNotAllowedTest {

    /**
     * Test the default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        // Arrange
        MethodNotAllowed exception = new MethodNotAllowed();

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getMessage().isEmpty());
    }

    /**
     * Test the constructor with a message.
     */
    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "Method not allowed";
        MethodNotAllowed exception = new MethodNotAllowed(message);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a message format and parameters.
     */
    @Test
    public void testConstructorWithMessageFormatAndParameters() {
        // Arrange
        String messageFormat = "Method %s not allowed";
        Object[] params = {"POST"};
        MethodNotAllowed exception = new MethodNotAllowed(messageFormat, params);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertEquals("Method POST not allowed", exception.getMessage());
    }

    /**
     * Test the constructor with a cause.
     */
    @Test
    public void testConstructorWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("Test cause");
        MethodNotAllowed exception = new MethodNotAllowed(cause);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }

    /**
     * Test the constructor with a cause and a message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Test cause");
        String message = "Method not allowed";
        MethodNotAllowed exception = new MethodNotAllowed(cause, message);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a cause, a message format, and parameters.
     */
    @Test
    public void testConstructorWithCauseMessageFormatAndParameters() {
        // Arrange
        Throwable cause = new RuntimeException("Test cause");
        String messageFormat = "Method %s not allowed";
        Object[] params = {"POST"};
        MethodNotAllowed exception = new MethodNotAllowed(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("Method POST not allowed", exception.getMessage());
    }

    /**
     * Test the constructor with a null cause.
     */
    @Test
    public void testConstructorWithNullCause() {
        // Arrange
        Throwable cause = null;
        MethodNotAllowed exception = new MethodNotAllowed(cause);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertNull(exception.getCause());
    }

    /**
     * Test the constructor with a null message.
     */
    @Test
    public void testConstructorWithNullMessage() {
        // Arrange
        String message = null;
        MethodNotAllowed exception = new MethodNotAllowed(message);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getMessage().isEmpty());
    }

    /**
     * Test the constructor with a non-empty error code.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCode() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        MethodNotAllowed exception = new MethodNotAllowed(errorCode);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertTrue(exception.getMessage().isEmpty());
    }

    /**
     * Test the constructor with a non-empty error code and a message.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCodeAndMessage() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        String message = "Method not allowed";
        MethodNotAllowed exception = new MethodNotAllowed(errorCode, message);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a non-empty error code, a message format, and parameters.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCodeMessageFormatAndParameters() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        String messageFormat = "Method %s not allowed";
        Object[] params = {"POST"};
        MethodNotAllowed exception = new MethodNotAllowed(errorCode, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("Method POST not allowed", exception.getMessage());
    }

    /**
     * Test the constructor with a non-empty error code and a cause.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCodeAndCause() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        Throwable cause = new RuntimeException("Test cause");
        MethodNotAllowed exception = new MethodNotAllowed(errorCode, cause);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }

    /**
     * Test the constructor with a non-empty error code, a message, and a cause.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCodeMessageAndCause() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        String message = "Method not allowed";
        Throwable cause = new RuntimeException("Test cause");
        MethodNotAllowed exception = new MethodNotAllowed(errorCode, message, cause);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }

    /**
     * Test the constructor with a non-empty error code, a message format, parameters, and a cause.
     */
    @Test
    public void testConstructorWithNonEmptyErrorCodeMessageFormatParametersAndCause() {
        // Arrange
        String errorCode = "ERR_METHOD_NOT_ALLOWED";
        String messageFormat = "Method %s not allowed";
        Object[] params = {"POST"};
        Throwable cause = new RuntimeException("Test cause");
        MethodNotAllowed exception = new MethodNotAllowed(errorCode, messageFormat, params, cause);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.getStatus());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals("Method POST not allowed", exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }
}

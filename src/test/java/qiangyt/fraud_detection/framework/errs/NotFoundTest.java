package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotFoundTest {

    /**
     * Test the constructor with error code, message format, and parameters.
     */
    @Test
    public void testConstructorWithErrorCodeMessageFormatAndParams() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_001;
        String messageFormat = "Resource not found: {0}";
        Object[] params = {"user"};

        NotFound exception = new NotFound(errorCode, messageFormat, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
    }

    /**
     * Test the constructor with error code and message.
     */
    @Test
    public void testConstructorWithErrorCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_002;
        String message = "User not found";

        NotFound exception = new NotFound(errorCode, message);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertEquals("User not found", exception.getMessage());
    }

    /**
     * Test the constructor with error code.
     */
    @Test
    public void testConstructorWithErrorCode() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_003;

        NotFound exception = new NotFound(errorCode);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertNull(exception.getMessage());
    }

    /**
     * Test the constructor with error code, cause, message format, and parameters.
     */
    @Test
    public void testConstructorWithErrorCodeCauseMessageFormatAndParams() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_004;
        String messageFormat = "Resource not found: {0}";
        Object[] params = {"user"};
        Throwable cause = new RuntimeException("Internal error");

        NotFound exception = new NotFound(errorCode, cause, messageFormat, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with error code, cause, and message.
     */
    @Test
    public void testConstructorWithErrorCodeCauseAndMessage() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_005;
        String message = "User not found";
        Throwable cause = new RuntimeException("Internal error");

        NotFound exception = new NotFound(errorCode, cause, message);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertEquals("User not found", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with error code and cause.
     */
    @Test
    public void testConstructorWithErrorCodeAndCause() {
        ErrorCode errorCode = ErrorCode.NOT_FOUND_006;
        Throwable cause = new RuntimeException("Internal error");

        NotFound exception = new NotFound(errorCode, cause);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(errorCode, exception.getCode());
        assertNull(exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with null error code, message format, and parameters.
     */
    @Test
    public void testConstructorWithNullErrorCodeMessageFormatAndParams() {
        String messageFormat = "Resource not found: {0}";
        Object[] params = {"user"};

        NotFound exception = new NotFound(null, messageFormat, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
    }

    /**
     * Test the constructor with null error code and message.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndMessage() {
        String message = "User not found";

        NotFound exception = new NotFound(null, message);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("User not found", exception.getMessage());
    }

    /**
     * Test the constructor with null error code and cause.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndCause() {
        Throwable cause = new RuntimeException("Internal error");

        NotFound exception = new NotFound(null, cause);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertNull(exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with null error code, message format, and parameters.
     */
    @Test
    public void testConstructorWithNullErrorCodeMessageFormatAndParams2() {
        String messageFormat = "Resource not found: {0}";
        Object[] params = {"user"};

        NotFound exception = new NotFound(null, cause, messageFormat, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with null error code, cause, and message.
     */
    @Test
    public void testConstructorWithNullErrorCodeCauseAndMessage2() {
        String message = "User not found";
        Throwable cause = new RuntimeException("Internal error");

        NotFound exception = new NotFound(null, cause, message);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("User not found", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    /**
     * Test the constructor with null error code and message format.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndMessageFormat() {
        String messageFormat = "Resource not found: {0}";

        NotFound exception = new NotFound(null, messageFormat);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: null", exception.getMessage());
    }

    /**
     * Test the constructor with null error code and parameters.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndParameters() {
        Object[] params = {"user"};

        NotFound exception = new NotFound(null, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
    }

    /**
     * Test the constructor with null error code and message format.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndMessageFormat2() {
        String messageFormat = "Resource not found: {0}";

        NotFound exception = new NotFound(null, cause, messageFormat);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: null", exception.getMessage());
    }

    /**
     * Test the constructor with null error code and parameters.
     */
    @Test
    public void testConstructorWithNullErrorCodeAndParameters2() {
        Object[] params = {"user"};

        NotFound exception = new NotFound(null, cause, params);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals("Resource not found: user", exception.getMessage());
    }
}

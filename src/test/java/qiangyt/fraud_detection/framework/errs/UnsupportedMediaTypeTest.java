package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnsupportedMediaTypeTest {

    /**
     * Test the default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        // Arrange
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType();
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getMessage().isEmpty());
    }

    /**
     * Test the constructor with a message.
     */
    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "Unsupported media type";
        
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType(message);
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a message format and parameters.
     */
    @Test
    public void testConstructorWithMessageFormatAndParams() {
        // Arrange
        String messageFormat = "Unsupported media type: %s";
        Object[] params = {"JSON"};
        
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType(messageFormat, params);
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertEquals("Unsupported media type: JSON", exception.getMessage());
    }

    /**
     * Test the constructor with a cause.
     */
    @Test
    public void testConstructorWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType(cause);
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause().equals(cause));
    }

    /**
     * Test the constructor with a cause and message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String message = "Unsupported media type";
        
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType(cause, message);
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause().equals(cause));
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a cause, message format, and parameters.
     */
    @Test
    public void testConstructorWithCauseMessageFormatAndParams() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String messageFormat = "Unsupported media type: %s";
        Object[] params = {"JSON"};
        
        // Act
        UnsupportedMediaType exception = new UnsupportedMediaType(cause, messageFormat, params);
        
        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getStatus());
        assertNull(exception.getErrorCode());
        assertTrue(exception.getCause().equals(cause));
        assertEquals("Unsupported media type: JSON", exception.getMessage());
    }
}

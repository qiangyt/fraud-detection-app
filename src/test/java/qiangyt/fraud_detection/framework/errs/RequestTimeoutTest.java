package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTimeoutTest {

    /**
     * Test the constructor with no parameters.
     */
    @Test
    public void testConstructorNoParams() {
        // Arrange
        // Act
        RequestTimeout exception = new RequestTimeout();
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().isEmpty());
    }

    /**
     * Test the constructor with a message.
     */
    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "Request timed out";
        
        // Act
        RequestTimeout exception = new RequestTimeout(message);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    /**
     * Test the constructor with a formatted message.
     */
    @Test
    public void testConstructorWithMessageFormat() {
        // Arrange
        String messageFormat = "Request timed out after %d seconds";
        int timeoutSeconds = 30;
        
        // Act
        RequestTimeout exception = new RequestTimeout(messageFormat, timeoutSeconds);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(String.valueOf(timeoutSeconds)));
    }

    /**
     * Test the constructor with a cause.
     */
    @Test
    public void testConstructorWithCause() {
        // Arrange
        Throwable cause = new Exception("Underlying error");
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getCause().getMessage().contains("Underlying error"));
    }

    /**
     * Test the constructor with a cause and a message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        // Arrange
        Throwable cause = new Exception("Underlying error");
        String message = "Request timed out";
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause, message);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(message));
        assertTrue(exception.getCause().getMessage().contains("Underlying error"));
    }

    /**
     * Test the constructor with a cause, a formatted message, and parameters.
     */
    @Test
    public void testConstructorWithCauseMessageFormat() {
        // Arrange
        Throwable cause = new Exception("Underlying error");
        String messageFormat = "Request timed out after %d seconds";
        int timeoutSeconds = 30;
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause, messageFormat, timeoutSeconds);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(String.valueOf(timeoutSeconds)));
        assertTrue(exception.getCause().getMessage().contains("Underlying error"));
    }

    /**
     * Test the constructor with a null cause.
     */
    @Test
    public void testConstructorWithNullCause() {
        // Arrange
        Throwable cause = null;
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertNull(exception.getCause());
    }

    /**
     * Test the constructor with a null message and formatted message.
     */
    @Test
    public void testConstructorWithNullMessageAndFormattedMessage() {
        // Arrange
        String message = null;
        String messageFormat = "Request timed out after %d seconds";
        int timeoutSeconds = 30;
        
        // Act
        RequestTimeout exception = new RequestTimeout(message, messageFormat, timeoutSeconds);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(String.valueOf(timeoutSeconds)));
    }

    /**
     * Test the constructor with a null cause and formatted message.
     */
    @Test
    public void testConstructorWithNullCauseAndFormattedMessage() {
        // Arrange
        Throwable cause = null;
        String messageFormat = "Request timed out after %d seconds";
        int timeoutSeconds = 30;
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause, messageFormat, timeoutSeconds);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(String.valueOf(timeoutSeconds)));
        assertNull(exception.getCause());
    }

    /**
     * Test the constructor with a null cause and message.
     */
    @Test
    public void testConstructorWithNullCauseAndMessage() {
        // Arrange
        Throwable cause = null;
        String message = "Request timed out";
        
        // Act
        RequestTimeout exception = new RequestTimeout(cause, message);
        
        // Assert
        assertEquals(HttpStatus.REQUEST_TIMEOUT, exception.getStatus());
        assertNull(exception.getCode());
        assertTrue(exception.getMessage().contains(message));
        assertNull(exception.getCause());
    }
}

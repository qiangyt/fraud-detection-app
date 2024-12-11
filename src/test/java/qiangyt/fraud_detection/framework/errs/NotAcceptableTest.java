package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotAcceptableTest {

    /**
     * Test the constructor with a formatted message.
     */
    @Test
    public void testConstructorWithFormattedMessage() {
        // Arrange
        String messageFormat = "Error: {0} is not acceptable.";
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Error: data is not acceptable.", error.getMessage());
    }

    /**
     * Test the constructor with a message.
     */
    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String message = "Data is not acceptable.";

        // Act
        NotAcceptable error = new NotAcceptable(message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Data is not acceptable.", error.getMessage());
    }

    /**
     * Test the constructor with no message.
     */
    @Test
    public void testConstructorWithNoMessage() {
        // Arrange

        // Act
        NotAcceptable error = new NotAcceptable();

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with a cause and a formatted message.
     */
    @Test
    public void testConstructorWithCauseAndFormattedMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String messageFormat = "Error: {0} is not acceptable.";
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Error: data is not acceptable.", error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a cause and a message.
     */
    @Test
    public void testConstructorWithCauseAndMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String message = "Data is not acceptable.";

        // Act
        NotAcceptable error = new NotAcceptable(cause, message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Data is not acceptable.", error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a cause.
     */
    @Test
    public void testConstructorWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");

        // Act
        NotAcceptable error = new NotAcceptable(cause);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a null cause and a formatted message.
     */
    @Test
    public void testConstructorWithNullCauseAndFormattedMessage() {
        // Arrange
        Throwable cause = null;
        String messageFormat = "Error: {0} is not acceptable.";
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Error: data is not acceptable.", error.getMessage());
        assertNull(error.getCause());
    }

    /**
     * Test the constructor with a null cause and a message.
     */
    @Test
    public void testConstructorWithNullCauseAndMessage() {
        // Arrange
        Throwable cause = null;
        String message = "Data is not acceptable.";

        // Act
        NotAcceptable error = new NotAcceptable(cause, message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertEquals("Data is not acceptable.", error.getMessage());
        assertNull(error.getCause());
    }

    /**
     * Test the constructor with a null cause.
     */
    @Test
    public void testConstructorWithNullCause() {
        // Arrange
        Throwable cause = null;

        // Act
        NotAcceptable error = new NotAcceptable(cause);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNull(error.getCause());
    }

    /**
     * Test the constructor with an empty formatted message.
     */
    @Test
    public void testConstructorWithEmptyFormattedMessage() {
        // Arrange
        String messageFormat = "";
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with an empty message.
     */
    @Test
    public void testConstructorWithEmptyMessage() {
        // Arrange
        String message = "";

        // Act
        NotAcceptable error = new NotAcceptable(message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with a null formatted message.
     */
    @Test
    public void testConstructorWithNullFormattedMessage() {
        // Arrange
        String messageFormat = null;
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with a null message.
     */
    @Test
    public void testConstructorWithNullMessage() {
        // Arrange
        String message = null;

        // Act
        NotAcceptable error = new NotAcceptable(message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
    }

    /**
     * Test the constructor with a cause and an empty formatted message.
     */
    @Test
    public void testConstructorWithCauseAndEmptyFormattedMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String messageFormat = "";
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a cause and an empty message.
     */
    @Test
    public void testConstructorWithCauseAndEmptyMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String message = "";

        // Act
        NotAcceptable error = new NotAcceptable(cause, message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a cause and a null formatted message.
     */
    @Test
    public void testConstructorWithCauseAndNullFormattedMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String messageFormat = null;
        Object[] params = {"data"};

        // Act
        NotAcceptable error = new NotAcceptable(cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNotNull(error.getCause());
    }

    /**
     * Test the constructor with a cause and a null message.
     */
    @Test
    public void testConstructorWithCauseAndNullMessage() {
        // Arrange
        Throwable cause = new RuntimeException("Caused by runtime exception");
        String message = null;

        // Act
        NotAcceptable error = new NotAcceptable(cause, message);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, error.getStatus());
        assertNull(error.getMessage());
        assertNotNull(error.getCause());
    }
}

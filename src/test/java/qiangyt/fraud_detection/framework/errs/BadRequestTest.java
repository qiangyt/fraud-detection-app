package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BadRequestTest {

    @Test
    public void testBadRequestWithErrorCodeAndMessageFormat() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String messageFormat = "Invalid request: %s";
        Object[] params = {"param1"};

        // Act
        BadRequest badRequest = new BadRequest(errorCode, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains("Invalid request: param1"));
    }

    @Test
    public void testBadRequestWithErrorCodeAndMessage() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String message = "Invalid request";

        // Act
        BadRequest badRequest = new BadRequest(errorCode, message);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertEquals(message, badRequest.getMessage());
    }

    @Test
    public void testBadRequestWithErrorCode() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;

        // Act
        BadRequest badRequest = new BadRequest(errorCode);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains("Invalid request"));
    }

    @Test
    public void testBadRequestWithErrorCodeAndCauseAndMessageFormat() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        Throwable cause = new Exception("Cause exception");
        String messageFormat = "Invalid request: %s";
        Object[] params = {"param1"};

        // Act
        BadRequest badRequest = new BadRequest(errorCode, cause, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains("Invalid request: param1"));
        assertSame(cause, badRequest.getCause());
    }

    @Test
    public void testBadRequestWithErrorCodeAndCauseAndMessage() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        Throwable cause = new Exception("Cause exception");
        String message = "Invalid request";

        // Act
        BadRequest badRequest = new BadRequest(errorCode, cause, message);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertEquals(message, badRequest.getMessage());
        assertSame(cause, badRequest.getCause());
    }

    @Test
    public void testBadRequestWithErrorCodeAndCause() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        Throwable cause = new Exception("Cause exception");

        // Act
        BadRequest badRequest = new BadRequest(errorCode, cause);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains("Invalid request"));
        assertSame(cause, badRequest.getCause());
    }

    @Test
    public void testBadRequestWithNullErrorCode() {
        // Arrange
        ErrorCode errorCode = null;
        String messageFormat = "Invalid request: %s";
        Object[] params = {"param1"};

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new BadRequest(errorCode, messageFormat, params));
    }

    @Test
    public void testBadRequestWithEmptyMessageFormat() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String messageFormat = "";
        Object[] params = {"param1"};

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new BadRequest(errorCode, messageFormat, params));
    }

    @Test
    public void testBadRequestWithNullMessageFormat() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String messageFormat = null;
        Object[] params = {"param1"};

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new BadRequest(errorCode, messageFormat, params));
    }

    @Test
    public void testBadRequestWithNullParams() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String messageFormat = "Invalid request: %s";
        Object[] params = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new BadRequest(errorCode, messageFormat, params));
    }

    @Test
    public void testBadRequestWithEmptyParams() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String messageFormat = "Invalid request: %s";
        Object[] params = {};

        // Act
        BadRequest badRequest = new BadRequest(errorCode, messageFormat, params);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains("Invalid request:"));
    }

    @Test
    public void testBadRequestWithNullMessage() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String message = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new BadRequest(errorCode, message));
    }

    @Test
    public void testBadRequestWithEmptyMessage() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        String message = "";

        // Act
        BadRequest badRequest = new BadRequest(errorCode, message);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatus());
        assertEquals(errorCode, badRequest.getCode());
        assertTrue(badRequest.getMessage().contains(""));
    }

    @Test
    public void testBadRequestWithNullCause() {
        // Arrange
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        Throwable cause = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new BadRequest(errorCode, cause));
    }
}

!!!!test_begin!!!!

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.framework.errs.ErrorCode;
import qiangyt.fraud_detection.framework.errs.ErrorResponse;

public class ErrorResponseTest {

    @Test
    public void testHappyPath() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = {"resourceId", "123"};
        String path = "/api/resource";

        // Act
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .code(code)
                .message(message)
                .params(params)
                .path(path)
                .build();

        // Assert
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertArrayEquals(params, response.getParams());
        assertEquals(path, response.getPath());
    }

    @Test
    public void testNullTimestamp() {
        // Arrange
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = {"resourceId", "123"};
        String path = "/api/resource";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder()
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testEmptyError() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = {"resourceId", "123"};
        String path = "/api/resource";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testNullCode() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = null;
        String message = "The requested resource was not found.";
        Object[] params = {"resourceId", "123"};
        String path = "/api/resource";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testEmptyMessage() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "";
        Object[] params = {"resourceId", "123"};
        String path = "/api/resource";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testNullPath() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = {"resourceId", "123"};
        String path = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testEmptyParams() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = {};
        String path = "/api/resource";

        // Act
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .code(code)
                .message(message)
                .params(params)
                .path(path)
                .build();

        // Assert
        assertArrayEquals(params, response.getParams());
    }

    @Test
    public void testNullParams() {
        // Arrange
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        ErrorCode code = ErrorCode.NOT_FOUND;
        String message = "The requested resource was not found.";
        Object[] params = null;
        String path = "/api/resource";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .code(code)
                    .message(message)
                    .params(params)
                    .path(path)
                    .build();
        });
    }

    @Test
    public void testEmptyErrorResponse() {
        // Arrange
        Instant timestamp = Instant.now();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder()
                    .timestamp(timestamp)
                    .build();
        });
    }
}
!!!!test_end!!!!

package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class InternalTest {

    @Test
    public void testInternalWithMessageFormat() {
        var ex = new Internal("Internal error: %s", "error1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
    }

    @Test
    public void testInternalWithMessage() {
        var ex = new Internal("Internal error");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
    }

    @Test
    public void testInternalWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause, "Internal error: %s", "error1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error: error1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testInternalWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause, "Internal error");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testInternalWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Internal(cause);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Internal Server Error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

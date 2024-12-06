package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MethodNotAllowedTest {

    @Test
    public void testMethodNotAllowedWithMessageFormat() {
        var ex = new MethodNotAllowed("Invalid method: %s", "POST");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
    }

    @Test
    public void testMethodNotAllowedWithMessage() {
        var ex = new MethodNotAllowed("Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
    }

    @Test
    public void testMethodNotAllowedWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause, "Invalid method: %s", "POST");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Invalid method: POST", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testMethodNotAllowedWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause, "Method not allowed");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method not allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testMethodNotAllowedWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new MethodNotAllowed(cause);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Method Not Allowed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

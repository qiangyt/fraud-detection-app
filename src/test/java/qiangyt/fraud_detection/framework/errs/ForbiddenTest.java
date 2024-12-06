package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ForbiddenTest {

    @Test
    public void testForbiddenWithMessageFormat() {
        var ex = new Forbidden(ErrorCode.NONE, "Access denied: %s", "resource1");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied: resource1", ex.getMessage());
    }

    @Test
    public void testForbiddenWithMessage() {
        var ex = new Forbidden(ErrorCode.NONE, "Access denied");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied", ex.getMessage());
    }

    @Test
    public void testForbiddenWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause, "Access denied: %s", "resource1");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied: resource1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testForbiddenWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause, "Access denied");
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Access denied", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testForbiddenWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Forbidden(ErrorCode.NONE, cause);
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Forbidden", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

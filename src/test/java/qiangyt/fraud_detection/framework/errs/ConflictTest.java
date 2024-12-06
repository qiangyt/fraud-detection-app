package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ConflictTest {

    @Test
    public void testConflictWithMessageFormat() {
        var ex = new Conflict("Resource conflict: %s", "resource1");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict: resource1", ex.getMessage());
    }

    @Test
    public void testConflictWithMessage() {
        var ex = new Conflict("Resource conflict");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict", ex.getMessage());
    }

    @Test
    public void testConflictWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause, "Resource conflict: %s", "resource1");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict: resource1", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConflictWithCauseAndMessage() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause, "Resource conflict");
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Resource conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testConflictWithCause() {
        var cause = new RuntimeException("Root cause");
        var ex = new Conflict(cause);
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

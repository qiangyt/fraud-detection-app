package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RequestTimeoutTest {

    @Test
    void testRequestTimeoutWithMessageFormat() {
        var ex = new RequestTimeout("Timeout after %d seconds", 30);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithMessage() {
        var ex = new RequestTimeout("Timeout occurred");
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithoutMessage() {
        var ex = new RequestTimeout();
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
    }

    @Test
    void testRequestTimeoutWithCauseAndMessageFormat() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause, "Timeout after %d seconds", 30);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout after 30 seconds", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testRequestTimeoutWithCauseAndMessage() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause, "Timeout occurred");
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals("Timeout occurred", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testRequestTimeoutWithCauseOnly() {
        var cause = new RuntimeException("Underlying cause");
        var ex = new RequestTimeout(cause);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, ex.getStatus());
        assertEquals(ErrorCode.NONE, ex.getCode());
        assertEquals(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}

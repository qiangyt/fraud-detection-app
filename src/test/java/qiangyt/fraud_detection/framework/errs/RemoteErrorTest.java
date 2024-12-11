package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RemoteErrorTest {

    private ErrorResponse errorResponse;

    @BeforeEach
    public void setUp() {
        errorResponse = new ErrorResponse("404", "Not Found", null);
    }

    /**
     * Test the constructor with a valid ErrorResponse.
     */
    @Test
    public void testConstructorWithValidErrorResponse() {
        RemoteError remoteError = new RemoteError(errorResponse);

        assertEquals("404", remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNull(remoteError.getParams());
        assertEquals(errorResponse, remoteError.getResponse());
    }

    /**
     * Test the constructor with a null ErrorResponse.
     */
    @Test
    public void testConstructorWithNullErrorResponse() {
        assertThrows(NullPointerException.class, () -> new RemoteError(null));
    }

    /**
     * Test the constructor with an empty ErrorResponse.
     */
    @Test
    public void testConstructorWithEmptyErrorResponse() {
        ErrorResponse emptyResponse = new ErrorResponse("", "", null);
        RemoteError remoteError = new RemoteError(emptyResponse);

        assertEquals("", remoteError.getCode());
        assertEquals("", remoteError.getMessage());
        assertNull(remoteError.getParams());
        assertEquals(emptyResponse, remoteError.getResponse());
    }

    /**
     * Test the constructor with a null code in ErrorResponse.
     */
    @Test
    public void testConstructorWithNullCodeInErrorResponse() {
        ErrorResponse responseWithNullCode = new ErrorResponse(null, "Not Found", null);
        RemoteError remoteError = new RemoteError(responseWithNullCode);

        assertNull(remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNull(remoteError.getParams());
        assertEquals(responseWithNullCode, remoteError.getResponse());
    }

    /**
     * Test the constructor with a null message in ErrorResponse.
     */
    @Test
    public void testConstructorWithNullMessageInErrorResponse() {
        ErrorResponse responseWithNullMessage = new ErrorResponse("404", null, null);
        RemoteError remoteError = new RemoteError(responseWithNullMessage);

        assertEquals("404", remoteError.getCode());
        assertNull(remoteError.getMessage());
        assertNull(remoteError.getParams());
        assertEquals(responseWithNullMessage, remoteError.getResponse());
    }

    /**
     * Test the constructor with a non-empty params in ErrorResponse.
     */
    @Test
    public void testConstructorWithNonEmptyParamsInErrorResponse() {
        ErrorResponse responseWithParams = new ErrorResponse("404", "Not Found", Collections.singletonMap("key", "value"));
        RemoteError remoteError = new RemoteError(responseWithParams);

        assertEquals("404", remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNotNull(remoteError.getParams());
        assertEquals(Collections.singletonMap("key", "value"), remoteError.getParams());
        assertEquals(responseWithParams, remoteError.getResponse());
    }

    /**
     * Test the constructor with a non-null but empty params in ErrorResponse.
     */
    @Test
    public void testConstructorWithEmptyParamsInErrorResponse() {
        ErrorResponse responseWithEmptyParams = new ErrorResponse("404", "Not Found", Collections.emptyMap());
        RemoteError remoteError = new RemoteError(responseWithEmptyParams);

        assertEquals("404", remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNotNull(remoteError.getParams());
        assertTrue(remoteError.getParams().isEmpty());
        assertEquals(responseWithEmptyParams, remoteError.getResponse());
    }

    /**
     * Test the constructor with a null params in ErrorResponse.
     */
    @Test
    public void testConstructorWithNullParamsInErrorResponse() {
        ErrorResponse responseWithNullParams = new ErrorResponse("404", "Not Found", null);
        RemoteError remoteError = new RemoteError(responseWithNullParams);

        assertEquals("404", remoteError.getCode());
        assertEquals("Not Found", remoteError.getMessage());
        assertNull(remoteError.getParams());
        assertEquals(responseWithNullParams, remoteError.getResponse());
    }
}

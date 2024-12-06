package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RemoteErrorTest {

    @Test
    public void testRemoteError() {
        ErrorResponse response =
                ErrorResponse.builder()
                        .code(ErrorCode.PARAMETER_NOT_VALID)
                        .message("Invalid parameter")
                        .params(new Object[] {"param1"})
                        .build();

        RemoteError remoteError = new RemoteError(response);

        assertEquals(ErrorCode.PARAMETER_NOT_VALID, remoteError.getCode());
        assertEquals("Invalid parameter", remoteError.getMessage());
        assertArrayEquals(new Object[] {"param1"}, remoteError.getParams());
        assertEquals(response, remoteError.getResponse());
    }
}

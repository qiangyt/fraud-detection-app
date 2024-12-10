/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.framework.errs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit test for {@link RemoteError}. */
public class RemoteErrorTest {

    /** Tests the creation and properties of {@link RemoteError}. */
    @Test
    public void testRemoteError() {
        // Create an ErrorResponse with specific parameters
        ErrorResponse response =
                ErrorResponse.builder()
                        .code(ErrorCode.PARAMETER_NOT_VALID)
                        .message("Invalid parameter")
                        .params(new Object[] {"param1"})
                        .build();

        // Create a RemoteError using the ErrorResponse
        RemoteError remoteError = new RemoteError(response);

        // Verify the properties of the RemoteError
        assertEquals(ErrorCode.PARAMETER_NOT_VALID, remoteError.getCode());
        assertEquals("Invalid parameter", remoteError.getMessage());
        assertArrayEquals(new Object[] {"param1"}, remoteError.getParams());
        assertEquals(response, remoteError.getResponse());
    }
}

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
package qiangyt.fraud_detection.framework.misc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class CodecTest {

    @Test
    public void testIsBase64Encoded_HappyPath() {
        // Given a Base64 encoded string
        String base64String = "SGVsbG8gV29ybGQh";

        // When checking if it's Base64 encoded
        boolean result =
                Codec.isBase64Encoded(
                        new MockMultipartFile(
                                "file", "test.txt", "text/plain", base64String.getBytes()));

        // Then it should return true
        assertTrue(result);
    }

    @Test
    public void testIsBase64Encoded_NegativePath() {
        // Given a non-Base64 encoded string
        String nonBase64String = "Hello World!";

        // When checking if it's Base64 encoded
        boolean result =
                Codec.isBase64Encoded(
                        new MockMultipartFile(
                                "file", "test.txt", "text/plain", nonBase64String.getBytes()));

        // Then it should return false
        assertFalse(result);
    }
}

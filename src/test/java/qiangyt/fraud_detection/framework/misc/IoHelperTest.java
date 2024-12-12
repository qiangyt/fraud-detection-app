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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.framework.errs.Internal;

public class IoHelperTest {

    @Test
    public void testGetFileExtensionFromFileNameWithExtension() {
        // Arrange
        String fileName = "example.txt";

        // Act
        String result = IoHelper.getFileExtension(fileName);

        // Assert
        assertEquals("txt", result);
    }

    @Test
    public void testGetFileExtensionFromFileNameWithoutExtension() {
        // Arrange
        String fileName = "example";

        // Act
        String result = IoHelper.getFileExtension(fileName);

        // Assert
        assertEquals("", result);
    }

    @Test
    public void testGetFileExtensionFromFileWithExtension() {
        // Arrange
        File file = new File("example.txt");

        // Act
        String result = IoHelper.getFileExtension(file);

        // Assert
        assertEquals("txt", result);
    }

    @Test
    public void testGetFileExtensionFromFileWithoutExtension() {
        // Arrange
        File file = new File("example");

        // Act
        String result = IoHelper.getFileExtension(file);

        // Assert
        assertEquals("", result);
    }

    @Test
    public void testReadFullyWithEmptyInputStream() throws IOException {
        // Arrange
        var inputStream = new ByteArrayInputStream(new byte[0]);

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertTrue(result.length == 0);
    }

    @Test
    public void testReadFullyWithNonEmptyInputStream() throws IOException {
        // Arrange
        String inputString = "Hello, World!";
        var inputStream = new ByteArrayInputStream(inputString.getBytes());

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertEquals(inputString, new String(result));
    }

    @Test
    public void testReadFullyWithIOException() {
        // Arrange
        var inputStream =
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        throw new IOException("Simulated I/O error");
                    }
                };

        // Act & Assert
        assertThrows(Internal.class, () -> IoHelper.readFully(inputStream));
    }

    @Test
    public void testReadFullyWithLargeInputStream() throws IOException {
        // Arrange
        int size = 1024 * 1024; // 1MB
        byte[] inputBytes = new byte[size];
        for (int i = 0; i < size; i++) {
            inputBytes[i] = (byte) i;
        }
        var inputStream = new ByteArrayInputStream(inputBytes);

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertEquals(size, result.length);
        for (int i = 0; i < size; i++) {
            assertEquals((byte) i, result[i]);
        }
    }

    // @Test
    // public void testReadFullyWithNullInputStream() {
    //     // Arrange
    //     var inputStream = null;

    //     // Act & Assert
    //     assertThrows(NullPointerException.class, () -> IoHelper.readFully(inputStream));
    // }
}

!!!!test_begin!!!!

import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.framework.errs.Internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertTrue(result.length == 0);
    }

    @Test
    public void testReadFullyWithNonEmptyInputStream() throws IOException {
        // Arrange
        String inputString = "Hello, World!";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertEquals(inputString, new String(result));
    }

    @Test
    public void testReadFullyWithIOException() {
        // Arrange
        InputStream inputStream = new InputStream() {
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
        InputStream inputStream = new ByteArrayInputStream(inputBytes);

        // Act
        byte[] result = IoHelper.readFully(inputStream);

        // Assert
        assertEquals(size, result.length);
        for (int i = 0; i < size; i++) {
            assertEquals((byte) i, result[i]);
        }
    }

    @Test
    public void testReadFullyWithNullInputStream() {
        // Arrange
        InputStream inputStream = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> IoHelper.readFully(inputStream));
    }
}
!!!!test_end!!!!

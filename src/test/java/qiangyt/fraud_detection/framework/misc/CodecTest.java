package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CodecTest {

    @Test
    public void testIsBase64Encoded_HappyPath() {
        // Given a Base64 encoded string
        String base64String = "SGVsbG8gV29ybGQh";

        // When checking if it's Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", base64String.getBytes()));

        // Then it should return true
        assertTrue(result);
    }

    @Test
    public void testIsBase64Encoded_NegativePath() {
        // Given a non-Base64 encoded string
        String nonBase64String = "Hello World!";

        // When checking if it's Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", nonBase64String.getBytes()));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_EmptyFile() {
        // Given an empty file
        File emptyFile = new File("empty.txt");

        // When checking if its content is Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_FileWithNonBase64Lines() {
        // Given a file with non-Base64 encoded lines
        String content = "Hello\nWorld!";
        File nonBase64File = new File("nonBase64.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nonBase64File))) {
            writer.write(content);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // When checking if its content is Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", Files.readAllBytes(nonBase64File.toPath())));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_FileWithBase64Lines() {
        // Given a file with Base64 encoded lines
        String content = "SGVsbG8gV29ybGQh\nUHl0aG9uIGlzIEkgbXkgYmFzZTY0IGVuY29kZWQgc3RyaW5nIQ==";
        File base64File = new File("base64.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(base64File))) {
            writer.write(content);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // When checking if its content is Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", Files.readAllBytes(base64File.toPath())));

        // Then it should return true
        assertTrue(result);
    }

    @Test
    public void testIsBase64Encoded_NullFile() {
        // Given a null file
        File nullFile = null;

        // When checking if its content is Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_EmptyString() {
        // Given an empty string
        String emptyString = "";

        // When checking if it's Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", emptyString.getBytes()));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_NullString() {
        // Given a null string
        String nullString = null;

        // When checking if it's Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]));

        // Then it should return false
        assertFalse(result);
    }

    @Test
    public void testIsBase64Encoded_FileWithMixedLines() {
        // Given a file with mixed Base64 and non-Base64 encoded lines
        String content = "SGVsbG8gV29ybGQh\nHello World!";
        File mixedFile = new File("mixed.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mixedFile))) {
            writer.write(content);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // When checking if its content is Base64 encoded
        boolean result = Codec.isBase64Encoded(new MockMultipartFile("file", "test.txt", "text/plain", Files.readAllBytes(mixedFile.toPath())));

        // Then it should return false
        assertFalse(result);
    }
}

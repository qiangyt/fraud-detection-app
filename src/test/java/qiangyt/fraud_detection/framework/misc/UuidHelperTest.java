package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UuidHelperTest {

    /**
     * Test the shortUuid method with a happy path.
     */
    @Test
    public void testShortUuidHappyPath() {
        // Generate a short UUID string
        String shortUuid = UuidHelper.shortUuid();
        
        // Check if the length of the generated UUID is 22 characters
        assertEquals(22, shortUuid.length());
        
        // Check if the generated UUID is not null
        assertNotNull(shortUuid);
    }

    /**
     * Test the compress method with a happy path.
     */
    @Test
    public void testCompressHappyPath() {
        // Generate a UUID object
        UUID uuid = UUID.randomUUID();
        
        // Compress the UUID object into a short UUID string
        String compressedUuid = UuidHelper.compress(uuid);
        
        // Check if the length of the compressed UUID is 22 characters
        assertEquals(22, compressedUuid.length());
        
        // Check if the compressed UUID is not null
        assertNotNull(compressedUuid);
    }

    /**
     * Test the uncompress method with a happy path.
     */
    @Test
    public void testUncompressHappyPath() {
        // Generate a UUID object
        UUID uuid = UUID.randomUUID();
        
        // Compress the UUID object into a short UUID string
        String compressedUuid = UuidHelper.compress(uuid);
        
        // Decompress the short UUID string into a UUID object
        UUID uncompressedUuid = UuidHelper.uncompress(compressedUuid);
        
        // Check if the original UUID and the uncompressed UUID are equal
        assertEquals(uuid, uncompressedUuid);
    }

    /**
     * Test the uncompress method with an invalid compressed UUID string.
     */
    @Test
    public void testUncompressInvalidCompressedUuid() {
        // Define an invalid compressed UUID string
        String invalidCompressedUuid = "invalid";
        
        // Try to decompress the invalid compressed UUID string and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> UuidHelper.uncompress(invalidCompressedUuid));
    }

    /**
     * Test the uncompress method with a null compressed UUID string.
     */
    @Test
    public void testUncompressNullCompressedUuid() {
        // Define a null compressed UUID string
        String nullCompressedUuid = null;
        
        // Try to decompress the null compressed UUID string and expect a NullPointerException
        assertThrows(NullPointerException.class, () -> UuidHelper.uncompress(nullCompressedUuid));
    }

    /**
     * Test the compress method with a null UUID object.
     */
    @Test
    public void testCompressNullUuid() {
        // Define a null UUID object
        UUID nullUuid = null;
        
        // Try to compress the null UUID object and expect a NullPointerException
        assertThrows(NullPointerException.class, () -> UuidHelper.compress(nullUuid));
    }

    /**
     * Test the shortUuid method with a null UUID object.
     */
    @Test
    public void testShortUuidNullUuid() {
        // Define a null UUID object
        UUID nullUuid = null;
        
        // Try to generate a short UUID string from the null UUID object and expect a NullPointerException
        assertThrows(NullPointerException.class, () -> UuidHelper.shortUuid());
    }

    /**
     * Test the uncompress method with an empty compressed UUID string.
     */
    @Test
    public void testUncompressEmptyCompressedUuid() {
        // Define an empty compressed UUID string
        String emptyCompressedUuid = "";
        
        // Try to decompress the empty compressed UUID string and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> UuidHelper.uncompress(emptyCompressedUuid));
    }

    /**
     * Test the compress method with a UUID object that is all zeros.
     */
    @Test
    public void testCompressAllZerosUuid() {
        // Define a UUID object that is all zeros
        UUID allZerosUuid = new UUID(0, 0);
        
        // Compress the UUID object into a short UUID string
        String compressedUuid = UuidHelper.compress(allZerosUuid);
        
        // Check if the length of the compressed UUID is 22 characters
        assertEquals(22, compressedUuid.length());
        
        // Check if the compressed UUID is not null
        assertNotNull(compressedUuid);
    }
}

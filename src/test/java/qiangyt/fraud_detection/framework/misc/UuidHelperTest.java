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

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UuidHelperTest {

    /** Test the shortUuid method with a happy path. */
    @Test
    public void testShortUuidHappyPath() {
        // Generate a short UUID string
        String shortUuid = UuidHelper.shortUuid();

        // Check if the length of the generated UUID is 22 characters
        assertEquals(22, shortUuid.length());

        // Check if the generated UUID is not null
        assertNotNull(shortUuid);
    }

    /** Test the compress method with a happy path. */
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

    /** Test the uncompress method with a happy path. */
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

    /** Test the uncompress method with an invalid compressed UUID string. */
    @Test
    public void testUncompressInvalidCompressedUuid() {
        // Define an invalid compressed UUID string
        String invalidCompressedUuid = "invalid";

        // Try to decompress the invalid compressed UUID string and expect an
        // IllegalArgumentException
        assertThrows(
                IllegalArgumentException.class, () -> UuidHelper.uncompress(invalidCompressedUuid));
    }

    /** Test the uncompress method with a null compressed UUID string. */
    @Test
    public void testUncompressNullCompressedUuid() {
        // Define a null compressed UUID string
        String nullCompressedUuid = null;

        // Try to decompress the null compressed UUID string and expect a NullPointerException
        assertThrows(NullPointerException.class, () -> UuidHelper.uncompress(nullCompressedUuid));
    }

    /** Test the compress method with a null UUID object. */
    @Test
    public void testCompressNullUuid() {
        // Define a null UUID object
        UUID nullUuid = null;

        // Try to compress the null UUID object and expect a NullPointerException
        assertThrows(NullPointerException.class, () -> UuidHelper.compress(nullUuid));
    }

    /** Test the uncompress method with an empty compressed UUID string. */
    @Test
    public void testUncompressEmptyCompressedUuid() {
        // Define an empty compressed UUID string
        String emptyCompressedUuid = "";

        // Try to decompress the empty compressed UUID string and expect an IllegalArgumentException
        assertThrows(
                IllegalArgumentException.class, () -> UuidHelper.uncompress(emptyCompressedUuid));
    }

    /** Test the compress method with a UUID object that is all zeros. */
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

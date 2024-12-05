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

import java.util.UUID;

/** Utility class for UUID */
public class UuidHelper {

    /**
     * Generate short UUID string (22 characters)
     *
     * @return
     */
    public static String shortUuid() {
        var uuid = UUID.randomUUID();
        return compress(uuid);
    }

    /** Compress the UUID object into a short UUID string. */
    public static String compress(UUID uuid) {
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        Codec.longTobytes(most, byUuid, 0);
        Codec.longTobytes(least, byUuid, 8);
        return Codec.bytesToBase64(byUuid);
    }

    /** Decompress the short UUID string into a UUID object. */
    public static UUID uncompress(String compressedUuid) {
        if (compressedUuid.length() != 22) {
            throw new IllegalArgumentException("Invalid uuid!");
        }
        byte[] byUuid = Codec.base64ToBytes(compressedUuid);
        long most = Codec.bytesTolong(byUuid, 0);
        long least = Codec.bytesTolong(byUuid, 8);
        return new UUID(most, least);
    }
}

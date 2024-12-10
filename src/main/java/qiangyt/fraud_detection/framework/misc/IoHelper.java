/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import qiangyt.fraud_detection.framework.errs.Internal;

/** Utility class for IO operations. */
public class IoHelper {

    /**
     * Gets the file extension from a File object.
     *
     * @param file the file object
     * @return the file extension, or an empty string if none found
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * Gets the file extension from a file name.
     *
     * @param fileName the file name
     * @return the file extension, or an empty string if none found
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * Reads the entire content of an InputStream into a byte array.
     *
     * @param inputStream the input stream to read from
     * @return a byte array containing the data read from the input stream
     * @throws Internal if an IOException occurs during reading
     */
    public static byte[] readFully(InputStream inputStream) {
        try (var input = new BufferedInputStream(inputStream)) {
            var output = new ByteArrayOutputStream(1024);
            var buffer = new byte[1024];
            while (true) {
                int read = input.read(buffer);
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new Internal(e);
        }
    }
}

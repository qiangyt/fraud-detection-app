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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import qiangyt.fraud_detection.framework.errs.Internal;

/** Encoding and Decoding Tools */
@Slf4j
public class Codec {

    public static final Pattern BASE64_PATTERN = Pattern.compile("^[A-Za-z0-9+/=]+$");

    public static boolean isBase64Encoded(MultipartFile file) {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // 读取文件的前2行进行判断
            String line;
            int linesRead = 0;
            while ((line = reader.readLine()) != null && linesRead < 2) {
                linesRead++;
                // 如果行内容不符合 Base64 的正则表达式规则，则判断为非 Base64
                if (!BASE64_PATTERN.matcher(line).matches()) {
                    return false;
                }
            }
            // 如果读取的所有行都符合 Base64 规则，可能是 Base64 编码
            return true;
        } catch (IOException e) {
            log.error(
                    "failed to decode uploaded file" + file.getOriginalFilename(),
                    e); // TODO: structural log
            return false;
        }
    }

    /** Converts byte array encoded to Base64 (suitable for URL) */
    public static String bytesToBase64(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.encodeBase64URLSafeString(bytes);
    }

    public static String bytesToBase64DataUrl(byte[] bytes, String type) {
        String base64 = bytesToBase64(bytes);
        return "data:image/" + type + ";base64," + base64;
    }

    public static String fileToBase64DataUrl(File file) {
        String type = IoHelper.getFileExtension(file);
        byte[] data;
        try {
            data = IoHelper.readFully(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new Internal(e);
        }
        return bytesToBase64DataUrl(data, type);
    }

    public static String base64ToStr(String base64ed) {
        var bytes = base64ToBytes(base64ed);
        if (bytes == null) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /** Converts base64 to byte array */
    public static byte[] base64ToBytes(String base64ed) {
        if (base64ed == null) {
            return null;
        }
        return Base64.decodeBase64(base64ed + "==");
    }

    /** Converts long to byte array */
    static void longTobytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }

    /** Converts byte array to long */
    static long bytesTolong(byte[] bytes, int offset) {
        long r = 0;
        for (int i = 7; i > -1; i--) {
            r |= (((long) bytes[offset++]) & 0xFF) << 8 * i;
        }
        return r;
    }
}

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

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;

/** Utility class for string operations */
public class StringHelper {

    private static final String PHONE_WITH_COUNTRY_CODE_REGEX = "^\\+(\\d{1,3})(\\d+)$";
    private static final Pattern PHONE_WITH_COUNTRY_CODE_PATTERN =
            Pattern.compile(PHONE_WITH_COUNTRY_CODE_REGEX);

    public static Pair<String, String> parsePhoneNumber(String phoneNoOrOther) {
        if (isBlank(phoneNoOrOther)) {
            return null;
        }

        var matcher = PHONE_WITH_COUNTRY_CODE_PATTERN.matcher(phoneNoOrOther);
        if (matcher.matches()) {
            var countryCode = matcher.group(1);
            var phoneNo = matcher.group(2);
            return Pair.of(countryCode, phoneNo);
        }

        return null;
    }

    /** toString for array */
    public static <T> String toString(T[] array) {
        if (array == null) {
            return null;
        }
        return Lists.newArrayList(array).toString();
    }

    /**
     * Join multiple strings with a specified delimiter
     *
     * @param delimiter
     * @param texts Multiple strings to be concatenated
     */
    public static <T> String join(String delimiter, Collection<T> texts) {
        return join(delimiter, texts.toArray());
    }

    /**
     * toString for array with a specified delimiter
     *
     * @param delimiter
     * @param array array
     */
    public static <T> String join(String delimiter, T[] array) {
        var r = new StringBuilder(array.length * 64);
        var isFirst = true;
        for (var obj : array) {
            if (isFirst) {
                isFirst = false;
            } else if (delimiter != null) {
                r.append(delimiter);
            }
            r.append(Objects.toString(obj));
        }
        return r.toString();
    }

    /** determine if a string is null or consists entirely of whitespace characters i */
    public static boolean isBlank(String str) {
        return (str == null || str.length() == 0 || str.trim().length() == 0);
    }

    /** determine it is not null and not entirely composed of whitespace characters */
    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static String right(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(str.length() - length);
    }

    public static String left(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(0, length);
    }
}

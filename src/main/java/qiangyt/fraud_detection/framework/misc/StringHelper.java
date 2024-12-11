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

    /**
     * Parses a phone number with a country code.
     *
     * @param phoneNoOrOther the phone number string which may include a country code
     * @return a Pair containing the country code and phone number, or null if the input is blank or
     *     doesn't match the pattern
     */
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

    /**
     * Converts an array to a string representation.
     *
     * @param array the array to be converted
     * @param <T> the type of elements in the array
     * @return a string representation of the array, or null if the array is null
     */
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
        if (texts == null) {
            return null; // Handle null collection
        }
        return join(delimiter, texts.toArray());
    }

    /**
     * Joins an array of strings with a specified delimiter.
     *
     * @param delimiter the delimiter to be used between strings
     * @param array the array of strings to be concatenated
     * @param <T> the type of elements in the array
     * @return a concatenated string with the specified delimiter
     */
    public static <T> String join(String delimiter, T[] array) {
        if (array == null) {
            return null; // Handle null array
        }
        // Initialize a StringBuilder with an estimated capacity
        var r = new StringBuilder(array.length * 64);
        var isFirst = true;
        for (var obj : array) {
            if (isFirst) {
                isFirst = false; // Skip delimiter for the first element
            } else if (delimiter != null) {
                r.append(delimiter); // Append delimiter before each subsequent element
            }
            r.append(Objects.toString(obj)); // Append the string representation of the element
        }
        return r.toString(); // Return the concatenated string
    }

    /**
     * Determines if a string is null or consists entirely of whitespace characters.
     *
     * @param str the string to be checked
     * @return true if the string is null or blank, false otherwise
     */
    public static boolean isBlank(String str) {
        return (str == null || str.length() == 0 || str.trim().length() == 0);
    }

    /**
     * Determines if a string is not null and not entirely composed of whitespace characters.
     *
     * @param str the string to be checked
     * @return true if the string is not null and not blank, false otherwise
     */
    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Returns the rightmost part of a string with the specified length.
     *
     * @param str the string to be processed
     * @param length the number of characters to return from the right
     * @return the rightmost part of the string, or the original string if its length is less than
     *     or equal to the specified length
     */
    public static String right(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(str.length() - length);
    }

    /**
     * Returns the leftmost part of a string with the specified length.
     *
     * @param str the string to be processed
     * @param length the number of characters to return from the left
     * @return the leftmost part of the string, or the original string if its length is less than or
     *     equal to the specified length
     */
    public static String left(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(0, length);
    }
}

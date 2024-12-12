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

import com.google.common.collect.Lists;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class StringHelperTest {

    @Test
    public void testParsePhoneNumber_HappyPath() {
        // Given
        String phoneNo = "+1234567890";

        // When
        var result = StringHelper.parsePhoneNumber(phoneNo);

        // Then
        assertNotNull(result);
        assertEquals("123", result.getLeft());
        assertEquals("4567890", result.getRight());
    }

    @Test
    public void testParsePhoneNumber_NoCountryCode() {
        // Given
        String phoneNo = "1234567890";

        // When
        var result = StringHelper.parsePhoneNumber(phoneNo);

        // Then
        assertNull(result);
    }

    @Test
    public void testParsePhoneNumber_BlankInput() {
        // Given
        String phoneNo = "   ";

        // When
        var result = StringHelper.parsePhoneNumber(phoneNo);

        // Then
        assertNull(result);
    }

    @Test
    public void testParsePhoneNumber_NullInput() {
        // Given
        String phoneNo = null;

        // When
        var result = StringHelper.parsePhoneNumber(phoneNo);

        // Then
        assertNull(result);
    }

    @Test
    public void testToString_HappyPath() {
        // Given
        Integer[] array = {1, 2, 3, 4};

        // When
        String result = StringHelper.toString(array);

        // Then
        assertEquals("[1, 2, 3, 4]", result);
    }

    @Test
    public void testToString_NullArray() {
        // Given
        Integer[] array = null;

        // When
        String result = StringHelper.toString(array);

        // Then
        assertNull(result);
    }

    @Test
    public void testJoin_HappyPath() {
        // Given
        var texts = Lists.newArrayList("a", "b", "c");
        String delimiter = ",";

        // When
        String result = StringHelper.join(delimiter, texts);

        // Then
        assertEquals("a,b,c", result);
    }

    @Test
    public void testJoin_NullCollection() {
        // Given
        Collection<String> texts = null;
        String delimiter = ",";

        // When
        String result = StringHelper.join(delimiter, texts);

        // Then
        assertNull(result);
    }

    @Test
    public void testIsBlank_HappyPath() {
        // Given
        String str = "   ";

        // When
        boolean result = StringHelper.isBlank(str);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsBlank_NotBlankString() {
        // Given
        String str = "abc";

        // When
        boolean result = StringHelper.isBlank(str);

        // Then
        assertFalse(result);
    }

    @Test
    public void testRight_HappyPath() {
        // Given
        String str = "abcdefgh";
        int length = 3;

        // When
        String result = StringHelper.right(str, length);

        // Then
        assertEquals("fgh", result);
    }

    @Test
    public void testLeft_HappyPath() {
        // Given
        String str = "abcdefgh";
        int length = 3;

        // When
        String result = StringHelper.left(str, length);

        // Then
        assertEquals("abc", result);
    }
}

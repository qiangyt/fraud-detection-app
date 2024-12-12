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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for the {@link StringHelper#join(String, List)} method. */
public class TestStringHelper_joinCollection {

    private List<String> singleElementList;
    private List<String> multipleElementsList;
    private List<Integer> intList;
    private List<Object> mixedList;

    /** Sets up the test data before each test. */
    @BeforeEach
    public void setUp() {
        singleElementList = Arrays.asList("single");
        multipleElementsList = Arrays.asList("first", "second", "third");
        intList = Arrays.asList(1, 2, 3);
        mixedList = Arrays.asList("first", 2, null, "fourth");
    }

    /** Tests joining a single-element list with an empty delimiter. */
    @Test
    public void join_SingleElementListWithEmptyDelimiter_ReturnsSameString() {
        String result = StringHelper.join("", singleElementList);
        assertEquals("single", result);
    }

    /** Tests joining a single-element list with a delimiter. */
    @Test
    public void join_SingleElementListWithDelimiter_ReturnsSameString() {
        String result = StringHelper.join(" ", singleElementList);
        assertEquals("single", result);
    }

    /** Tests joining a multiple-elements list with an empty delimiter. */
    @Test
    public void join_MultipleElementsListWithEmptyDelimiter_ReturnsConcatenatedString() {
        String result = StringHelper.join("", multipleElementsList);
        assertEquals("firstsecondthird", result);
    }

    /** Tests joining a multiple-elements list with a delimiter. */
    @Test
    public void join_MultipleElementsListWithDelimiter_ReturnsConcatenatedStringWithDelimiter() {
        String result = StringHelper.join(" ", multipleElementsList);
        assertEquals("first second third", result);
    }

    /** Tests joining an empty list with a delimiter. */
    @Test
    public void join_EmptyListWithDelimiter_ReturnsEmptyString() {
        List<String> emptyList = Arrays.asList();
        String result = StringHelper.join(" ", emptyList);
        assertEquals("", result);
    }

    /** Tests joining a multiple-elements list with a null delimiter. */
    @Test
    public void join_NullDelimiterWithMultipleElements_ReturnsConcatenatedStringWithoutDelimiter() {
        String result = StringHelper.join(null, multipleElementsList);
        assertEquals("firstsecondthird", result);
    }

    /** Tests joining a list with null elements and an empty delimiter. */
    @Test
    public void join_EmptyDelimiterWithNullElements_ReturnsEmptyString() {
        List<String> nullElementsList = Arrays.asList(null, null, null);
        String result = StringHelper.join("", nullElementsList);
        assertEquals("nullnullnull", result);
    }

    /** Tests joining a list of non-string elements with a delimiter. */
    @Test
    public void join_NonStringElementsWithDelimiter_ReturnsConcatenatedString() {
        String result = StringHelper.join(" ", intList);
        assertEquals("1 2 3", result);
    }

    /** Tests joining a list with mixed types and an empty delimiter. */
    @Test
    public void join_MixedTypesWithEmptyDelimiter_ReturnsConcatenatedString() {
        String result = StringHelper.join("", mixedList);
        assertEquals("first2nullfourth", result);
    }

    /** Tests joining a list with mixed types and a non-empty delimiter. */
    @Test
    public void join_MixedTypesWithNonEmptyDelimiter_ReturnsConcatenatedString() {
        String result = StringHelper.join("-", mixedList);
        assertEquals("first-2-null-fourth", result);
    }
}

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

import qiangyt.fraud_detection.framework.misc.StringHelper;

public class TestStringHelper_joinCollection {

    private List<String> singleElementList;
    private List<String> multipleElementsList;

    @BeforeEach
    public void setUp() {
        singleElementList = Arrays.asList("single");
        multipleElementsList = Arrays.asList("first", "second", "third");
    }

    @Test
    public void join_SingleElementListWithEmptyDelimiter_ReturnsSameString() {
        String result = StringHelper.join("", singleElementList);
        assertEquals("single", result);
    }

    @Test
    public void join_SingleElementListWithDelimiter_ReturnsSameString() {
        String result = StringHelper.join(" ", singleElementList);
        assertEquals("single", result);
    }

    @Test
    public void join_MultipleElementsListWithEmptyDelimiter_ReturnsConcatenatedString() {
        String result = StringHelper.join("", multipleElementsList);
        assertEquals("firstsecondthird", result);
    }

    @Test
    public void join_MultipleElementsListWithDelimiter_ReturnsConcatenatedStringWithDelimiter() {
        String result = StringHelper.join(" ", multipleElementsList);
        assertEquals("first second third", result);
    }

    @Test
    public void join_EmptyListWithDelimiter_ReturnsEmptyString() {
        List<String> emptyList = Arrays.asList();
        String result = StringHelper.join(" ", emptyList);
        assertEquals("", result);
    }

    @Test
    public void join_NullDelimiterWithMultipleElements_ReturnsConcatenatedStringWithoutDelimiter() {
        String result = StringHelper.join(null, multipleElementsList);
        assertEquals("firstsecondthird", result);
    }

    @Test
    public void join_EmptyDelimiterWithNullElements_ReturnsEmptyString() {
        List<String> nullElementsList = Arrays.asList(null, null, null);
        String result = StringHelper.join("", nullElementsList);
        assertEquals("nullnullnull", result);
    }

    @Test
    public void join_NonStringElementsWithDelimiter_ReturnsConcatenatedString() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        String result = StringHelper.join(" ", intList);
        assertEquals("1 2 3", result);
    }
}

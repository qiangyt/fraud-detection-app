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
package qiangyt.fraud_detection.sdk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.framework.misc.UuidHelper;

public class DetectionResultTest {

    private DetectionReqEntity entity;
    private FraudCategory category;

    @BeforeEach
    public void setUp() {
        entity = new DetectionReqEntity();
        category = FraudCategory.BIG_AMOUNT;
    }

    /** Test the from method with valid parameters. */
    @Test
    public void testFromValidParameters() {
        var result = DetectionResult.from(entity, category);

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertTrue(result.isFraudulent());
        assertEquals(category, result.getCategory());
        assertEquals(category.message, result.getMessage());
        assertNotNull(result.getDetectedAt());
    }

    /** Test the from method with null entity. */
    @Test
    public void testFromNullEntity() {
        assertNull(DetectionResult.from(null, category));
    }

    /** Test the from method with null detectedAt. */
    @Test
    public void testFromNullDetectedAt() {
        var result =
                DetectionResult.builder()
                        .id(UuidHelper.shortUuid())
                        .entity(entity)
                        .fraudulent(category.yes)
                        .category(category)
                        .message(category.message)
                        .detectedAt(null)
                        .build();

        assertNotNull(result.getId());
        assertEquals(entity, result.getEntity());
        assertTrue(result.isFraudulent());
        assertEquals(category, result.getCategory());
        assertEquals(category.message, result.getMessage());
        assertNull(result.getDetectedAt());
    }
}

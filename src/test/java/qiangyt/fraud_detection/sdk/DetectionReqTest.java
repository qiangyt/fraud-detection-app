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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

/** Unit tests for the {@link DetectionReq} class. */
class DetectionReqTest {

    static final Validator v = Validation.buildDefaultValidatorFactory().getValidator();

    /** Tests that a valid {@link DetectionReq} instance passes validation. */
    @Test
    void testValidDetectionReq() {
        var req = DetectionReq.builder().accountId("12345").amount(100).memo("Test memo").build();

        var violations = v.validate(req);
        assertTrue(violations.isEmpty(), "There should be no constraint violations");
    }

    /** Tests that an invalid {@link DetectionReq} instance fails validation. */
    @Test
    void testInvalidDetectionReq() {
        var req = DetectionReq.builder().accountId("").memo("Test memo").build();

        var violations = v.validate(req);
        assertFalse(violations.isEmpty(), "There should be constraint violations");
    }
}

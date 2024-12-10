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
package qiangyt.fraud_detection.app.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

/** Unit tests for {@link ChainedDetectionEngine}. */
public class ChainedDetectionEngineTest {

    @InjectMocks ChainedDetectionEngine detectionEngine;

    @Mock DetectionRule rule1;

    @Mock DetectionRule rule2;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        detectionEngine.addRule(rule1);
        detectionEngine.addRule(rule2);
    }

    /** Tests detection when no fraud is detected by any rule. */
    @Test
    public void testDetect_NoFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(5000);
        entity.setAccountId("account789");

        // Mocking the detection rules to return no fraud
        when(rule1.detect(entity)).thenReturn(FraudCategory.NONE);
        when(rule2.detect(entity)).thenReturn(FraudCategory.NONE);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.NONE, result);
    }

    /** Tests detection when the first rule detects fraud. */
    @Test
    public void testDetect_FirstRuleFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(15000);
        entity.setAccountId("account789");

        // Mocking the detection rules to return fraud for the first rule
        when(rule1.detect(entity)).thenReturn(FraudCategory.BIG_AMOUNT);
        when(rule2.detect(entity)).thenReturn(FraudCategory.NONE);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.BIG_AMOUNT, result);
    }

    /** Tests detection when the second rule detects fraud. */
    @Test
    public void testDetect_SecondRuleFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(5000);
        entity.setAccountId("account123");

        // Mocking the detection rules to return fraud for the second rule
        when(rule1.detect(entity)).thenReturn(FraudCategory.NONE);
        when(rule2.detect(entity)).thenReturn(FraudCategory.SUSPICIOUS_ACCOUNT);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }
}

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

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

public class SimpleDetectionEngineTest {

    @InjectMocks SimpleDetectionEngine detectionEngine;

    @Mock DetectionRule rule1;

    @Mock DetectionRule rule2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        detectionEngine.getRules().clear();
        detectionEngine.getRules().add(rule1);
        detectionEngine.getRules().add(rule2);
    }

    @Test
    public void testDetect_NoFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(BigDecimal.valueOf(5000));
        entity.setAccountId("account789");

        when(rule1.apply(entity)).thenReturn(FraudCategory.NONE);
        when(rule2.apply(entity)).thenReturn(FraudCategory.NONE);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testDetect_FirstRuleFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(BigDecimal.valueOf(15000));
        entity.setAccountId("account789");

        when(rule1.apply(entity)).thenReturn(FraudCategory.BIG_AMOUNT);
        when(rule2.apply(entity)).thenReturn(FraudCategory.NONE);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.BIG_AMOUNT, result);
    }

    @Test
    public void testDetect_SecondRuleFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(BigDecimal.valueOf(5000));
        entity.setAccountId("account123");

        when(rule1.apply(entity)).thenReturn(FraudCategory.NONE);
        when(rule2.apply(entity)).thenReturn(FraudCategory.SUSPICIOUS_ACCOUNT);

        var result = detectionEngine.detect(entity);
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }
}

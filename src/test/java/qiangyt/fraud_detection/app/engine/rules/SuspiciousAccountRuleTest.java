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
package qiangyt.fraud_detection.app.engine.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.app.engine.ChainedDetectionEngine;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

public class SuspiciousAccountRuleTest {

    @Mock private ChainedDetectionEngine chain;

    @InjectMocks private SuspiciousAccountRule rule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        var props = new RuleProps();
        props.setMaxTransactionAmount(100);
        rule.setProps(props);
    }

    @Test
    public void testInit() {
        rule.init();
        verify(chain, times(1)).addRule(rule);
    }

    @Test
    public void testApply_NoFraud() {
        var entity = new DetectionReqEntity();
        entity.setAccountId("account789");

        var result = rule.detect(entity);
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testApply_SuspiciousAccountFraud() {
        var entity = new DetectionReqEntity();
        entity.setAccountId("fbiden");

        var result = rule.detect(entity);
        assertEquals(FraudCategory.SUSPICIOUS_ACCOUNT, result);
    }
}

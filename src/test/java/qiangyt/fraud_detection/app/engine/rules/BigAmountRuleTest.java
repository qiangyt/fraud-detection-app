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

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

public class BigAmountRuleTest {

    private BigAmountRule bigAmountRule;

    @BeforeEach
    public void setUp() {
        var props = new RuleProps();
        props.setMaxTransactionAmount(new BigDecimal(100));

        bigAmountRule = new BigAmountRule();
        bigAmountRule.setProps(props);
    }

    @Test
    public void testApply_NoFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(BigDecimal.valueOf(50));

        var result = bigAmountRule.apply(entity);
        assertEquals(FraudCategory.NONE, result);
    }

    @Test
    public void testApply_BigAmountFraud() {
        var entity = new DetectionReqEntity();
        entity.setAmount(BigDecimal.valueOf(150));

        var result = bigAmountRule.apply(entity);
        assertEquals(FraudCategory.BIG_AMOUNT, result);
    }
}

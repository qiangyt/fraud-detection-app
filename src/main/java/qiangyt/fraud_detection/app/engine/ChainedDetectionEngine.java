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

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.engine.rules.BigAmountRule;
import qiangyt.fraud_detection.app.engine.rules.SuspiciousAccountRule;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

/*
 * A simple rule-based detection system
 */
@lombok.Getter
@lombok.Setter
@Primary
@Service
@lombok.extern.slf4j.Slf4j
public class ChainedDetectionEngine implements DetectionEngine {

    final List<DetectionRule> rulesChain = new ArrayList<>();

    @Autowired BigAmountRule bigAmountRule;

    @Autowired SuspiciousAccountRule suspiciousAccountRule;

    public void addRule(DetectionRule rule) {
        getRulesChain().add(rule);
    }

    @Override
    public FraudCategory detect(DetectionReqEntity entity) {
        for (DetectionRule rule : getRulesChain()) {
            FraudCategory result = rule.detect(entity);
            if (result != FraudCategory.NONE) {
                return result;
            }
        }
        return FraudCategory.NONE;
    }
}

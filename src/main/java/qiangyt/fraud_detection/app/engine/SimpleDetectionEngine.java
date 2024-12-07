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

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
@lombok.extern.slf4j.Slf4j
public class SimpleDetectionEngine implements DetectionEngine {

    final List<DetectionRule> rules = new ArrayList<>();

    @Autowired BigAmountRule bigAmountRule;

    @Autowired SuspiciousAccountRule suspiciousAccountRule;

    @PostConstruct
    void init() {
        getRules().add(getBigAmountRule());
        getRules().add(getSuspiciousAccountRule());
    }

    @Override
    public FraudCategory detect(DetectionReqEntity entity) {
        for (DetectionRule rule : getRules()) {
            FraudCategory result = rule.apply(entity);
            if (result != FraudCategory.NONE) {
                return result;
            }
        }
        return FraudCategory.NONE;
    }
}

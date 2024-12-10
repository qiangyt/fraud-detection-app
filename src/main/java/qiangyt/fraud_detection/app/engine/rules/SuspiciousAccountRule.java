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

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.config.RuleProps;
import qiangyt.fraud_detection.app.engine.ChainedDetectionEngine;
import qiangyt.fraud_detection.app.engine.DetectionRule;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

@lombok.Getter
@lombok.Setter
@Service
public class SuspiciousAccountRule implements DetectionRule {

    @Autowired RuleProps props;

    @Autowired ChainedDetectionEngine chain;

    @PostConstruct
    void init() {
        chain.addRule(this);
    }

    @Override
    public FraudCategory detect(DetectionReqEntity entity) {
        if (getProps().getSuspicousAccounts().contains(entity.getAccountId())) {
            return FraudCategory.SUSPICIOUS_ACCOUNT;
        }
        return FraudCategory.NONE;
    }
}

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

/** This rule detects suspicious accounts based on predefined properties. */
@lombok.Getter
@lombok.Setter
@Service
public class SuspiciousAccountRule implements DetectionRule {

    @Autowired RuleProps props;

    @Autowired ChainedDetectionEngine chain;

    /** Initializes the rule and adds it to the detection engine chain. */
    @PostConstruct
    void init() {
        chain.addRule(this);
    }

    /**
     * Detects if the given entity's account ID is suspicious.
     *
     * @param entity the detection request entity containing account information
     * @return the fraud category, either SUSPICIOUS_ACCOUNT or NONE
     */
    @Override
    public FraudCategory detect(DetectionReqEntity entity) {
        if (getProps().getSuspicousAccounts().contains(entity.getAccountId())) {
            return FraudCategory.SUSPICIOUS_ACCOUNT;
        }
        return FraudCategory.NONE;
    }
}

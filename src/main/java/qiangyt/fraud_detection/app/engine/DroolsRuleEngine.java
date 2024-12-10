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

import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

/**
 * DroolsRuleEngine is a service that implements the DetectionEngine interface. It uses Drools rules
 * to detect fraud based on the provided DetectionReqEntity.
 *
 * <p>NOTE: not implemented yet
 */
@lombok.Getter
@lombok.Setter
@Service
@lombok.extern.slf4j.Slf4j
public class DroolsRuleEngine implements DetectionEngine {

    /**
     * Detects fraud by applying Drools rules to the given DetectionReqEntity.
     *
     * @param entity the detection request entity containing the data to be analyzed
     * @return the detected fraud category
     */
    @Override
    public FraudCategory detect(DetectionReqEntity entity) {
        // TODO: apply drools rules
        return FraudCategory.NONE;
    }
}

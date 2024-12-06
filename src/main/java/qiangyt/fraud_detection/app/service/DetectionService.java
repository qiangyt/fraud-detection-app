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
package qiangyt.fraud_detection.app.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionQueue;
import qiangyt.fraud_detection.sdk.DetectionApi;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

@Service
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
public class DetectionService implements DetectionApi {

    @Autowired DetectionQueue queue;

    @Autowired DetectionEngine engine;

    @Override
    public DetectionReqEntity submit(DetectionReq req) {
        var entity = req.toEntity();
        return getQueue().send(entity);
    }

    @Override
    public @NotNull DetectionResult detect(@NotNull DetectionReqEntity entity) {
        var category = getEngine().detect(entity);
        return DetectionResult.from(entity, category);
    }
}

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionQueue;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.FraudCategory;

public class DetectionServiceTest {

    @Mock DetectionQueue queue;

    @Mock DetectionEngine engine;

    @InjectMocks DetectionService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmit() {

        var entity = new DetectionReqEntity();
        var req =
                new DetectionReq() {
                    @Override
                    public DetectionReqEntity toEntity() {
                        return entity;
                    }
                };

        when(queue.send(any(DetectionReqEntity.class))).thenReturn(entity);

        var result = service.submit(req);

        assertEquals(entity, result);
    }

    @Test
    public void testDetect() {
        var entity = new DetectionReqEntity();
        var category = FraudCategory.BIG_AMOUNT;
        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(category);

        var result = service.detect(entity);

        assertEquals(category, result.getCategory());
        assertEquals(entity, result.getEntity());
    }
}

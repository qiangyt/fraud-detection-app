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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import qiangyt.fraud_detection.app.alert.Alerter;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionRequestQueue;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import qiangyt.fraud_detection.sdk.FraudCategory;

/** Unit tests for {@link DetectionService}. */
public class DetectionServiceTest {

    @Mock Alerter alertor;

    @Mock DetectionRequestQueue queue;

    @Mock DetectionEngine engine;

    ThreadPoolTaskExecutor detectionTaskExecutor = new ThreadPoolTaskExecutor();

    @InjectMocks DetectionService service;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        detectionTaskExecutor.initialize();

        service.setDetectionTaskExecutor(detectionTaskExecutor);
        service.setJackson(Jackson.DEFAULT);
    }

    /** Cleans up the test environment after each test. */
    @AfterEach
    public void tearDown() {
        service.shutdown();
        detectionTaskExecutor.shutdown(); // Added to properly shut down the executor
    }

    /** Tests the {@link DetectionService#submit(DetectionReq)} method. */
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

        // Submits a detection request and verifies the result
        var result = service.submit(req);

        assertEquals(entity, result);
    }

    /** Tests the {@link DetectionService#detectThenAlert(DetectionReqEntity)} method. */
    @Test
    public void testDetectThenAlert() throws Exception {
        var entity = new DetectionReqEntity();
        var category = FraudCategory.BIG_AMOUNT;

        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(category);
        doNothing().when(alertor).send(any(DetectionResult.class));

        // Detects fraud and sends an alert, then verifies the result
        var futureResult = service.detectThenAlert(entity);
        var detectionResult = futureResult.join();

        assertEquals(category, detectionResult.getCategory());
        assertEquals(entity, detectionResult.getEntity());
        verify(alertor, times(1)).send(detectionResult);
    }

    /**
     * Tests the {@link DetectionService#detectThenAlert(DetectionReqEntity)} method when no fraud
     * is detected.
     */
    @Test
    public void testDetectThenAlert_NoFraud() throws Exception {
        var entity = new DetectionReqEntity();
        var category = FraudCategory.NONE;

        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(category);
        doNothing().when(alertor).send(any(DetectionResult.class));

        // Detects no fraud and verifies the result
        var futureResult = service.detectThenAlert(entity);
        var detectionResult = futureResult.join();

        assertEquals(category, detectionResult.getCategory());
        assertEquals(entity, detectionResult.getEntity());
        verify(alertor, times(0)).send(detectionResult);
    }

    /** Tests the {@link DetectionService#detect(DetectionReqEntity)} method. */
    @Test
    public void testDetect() {
        var entity = new DetectionReqEntity();
        var category = FraudCategory.BIG_AMOUNT;
        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(category);

        // Detects fraud and verifies the result
        var result = service.detect(entity);

        assertEquals(category, result.getCategory());
        assertEquals(entity, result.getEntity());
    }

    /** Tests the {@link DetectionService#shutdown()} method. */
    @Test
    public void testShutdown() {
        var executor = spy(new ThreadPoolTaskExecutor());
        executor.initialize();
        service.setDetectionTaskExecutor(executor);

        // Shuts down the service and verifies the executor is shut down
        service.shutdown();

        verify(executor, times(1)).shutdown();
    }
}

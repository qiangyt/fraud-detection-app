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
package qiangyt.fraud_detection.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

public class FraudDetectionConfigTest {

    @Test
    public void testOpenApiInfo() {
        var t = new FraudDetectionConfig();

        OpenAPI openAPI = t.openApiInfo();
        assertNotNull(openAPI);
        assertEquals("Fraud Detection API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
    }

    @Test
    public void testDetectionTaskExecutor() {
        var t = new FraudDetectionConfig();
        var props = new DetectionTaskProps();

        var executor = t.detectionTaskExecutor(props);
        assertNotNull(executor);
        assertEquals(Runtime.getRuntime().availableProcessors(), executor.getCorePoolSize());
        assertEquals(Runtime.getRuntime().availableProcessors(), executor.getMaxPoolSize());
        assertEquals(props.getQueueCapacity(), executor.getQueueCapacity());
        assertEquals("detectionTask-", executor.getThreadNamePrefix());
        // assertEquals(fraudDetectionConfig.getDetectionTaskProps().getAwaitTerminationSeconds(),
        // executor.getAwaitTerminationSeconds());
    }

    @Test
    public void testSqsPollingThreadPool() {
        var t = new FraudDetectionConfig();
        var props = new SqsProps();

        var executorService = t.sqsPollingThreadPool(props);
        assertNotNull(executorService);
        assertEquals(
                props.getThreadPoolSize(),
                ((java.util.concurrent.ThreadPoolExecutor) executorService).getCorePoolSize());
    }
}

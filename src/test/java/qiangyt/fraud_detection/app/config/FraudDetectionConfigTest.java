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

/** Unit tests for {@link FraudDetectionConfig}. */
public class FraudDetectionConfigTest {

    /**
     * Tests the {@link FraudDetectionConfig#openApiInfo()} method. Ensures that the OpenAPI object
     * is correctly configured.
     */
    @Test
    public void testOpenApiInfo() {
        var t = new FraudDetectionConfig();

        OpenAPI openAPI = t.openApiInfo();
        assertNotNull(openAPI); // Check that the OpenAPI object is not null
        assertEquals("Fraud Detection API", openAPI.getInfo().getTitle()); // Verify the title
        assertEquals("1.0", openAPI.getInfo().getVersion()); // Verify the version
    }

    /**
     * Tests the {@link FraudDetectionConfig#detectionTaskExecutor(DetectionTaskProps)} method.
     * Ensures that the task executor is correctly configured.
     */
    @Test
    public void testDetectionTaskExecutor() {
        var t = new FraudDetectionConfig();
        var props = new DetectionTaskProps();

        var executor = t.detectionTaskExecutor(props);
        assertNotNull(executor); // Check that the executor is not null
        assertEquals(
                Runtime.getRuntime().availableProcessors(),
                executor.getCorePoolSize()); // Verify core pool size
        assertEquals(
                Runtime.getRuntime().availableProcessors(),
                executor.getMaxPoolSize()); // Verify max pool size
        assertEquals(
                props.getQueueCapacity(), executor.getQueueCapacity()); // Verify queue capacity
        assertEquals("detectionTask-", executor.getThreadNamePrefix()); // Verify thread name prefix
        // assertEquals(fraudDetectionConfig.getDetectionTaskProps().getAwaitTerminationSeconds(),
        // executor.getAwaitTerminationSeconds());
    }

    /**
     * Tests the {@link FraudDetectionConfig#sqsPollingThreadPool(SqsProps)} method. Ensures that
     * the SQS polling thread pool is correctly configured.
     */
    @Test
    public void testSqsPollingThreadPool() {
        var t = new FraudDetectionConfig();
        var props = new SqsProps();

        var executorService = t.sqsPollingThreadPool(props);
        assertNotNull(executorService); // Check that the executor service is not null
        assertEquals(
                props.getThreadPoolSize(),
                ((java.util.concurrent.ThreadPoolExecutor) executorService)
                        .getCorePoolSize()); // Verify thread pool size
    }
}

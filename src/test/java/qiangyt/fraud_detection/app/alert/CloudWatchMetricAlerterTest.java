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
package qiangyt.fraud_detection.app.alert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import qiangyt.fraud_detection.sdk.FraudCategory;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.PutMetricDataRequest;

/** Unit tests for {@link CloudWatchMetricAlerter}. */
public class CloudWatchMetricAlerterTest {

    @Mock CloudWatchClient client;

    @Mock GroupedAlerter group;

    @InjectMocks CloudWatchMetricAlerter alerter;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        alerter.init();
    }

    /**
     * Tests the {@link CloudWatchMetricAlerter#send(DetectionResult)} method.
     *
     * <p>This test verifies that the send method correctly sends a metric to CloudWatch. It creates
     * a DetectionReqEntity and a DetectionResult, then calls the send method. It captures the
     * PutMetricDataRequest sent to the CloudWatchClient and verifies that the namespace, metric
     * name, value, and dimensions are correctly set.
     */
    @Test
    public void testSend() {
        // Create a DetectionReqEntity
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();

        // Create a DetectionResult from the entity
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        // Call the send method
        alerter.send(result);

        // Capture the PutMetricDataRequest sent to the CloudWatchClient
        var captor = ArgumentCaptor.forClass(PutMetricDataRequest.class);
        verify(client).putMetricData(captor.capture());

        // Verify the captured request
        var req = captor.getValue();
        assertEquals("fraud", req.namespace());
        assertEquals(1, req.metricData().size());
        var metric = req.metricData().get(0);
        assertEquals("SUSPICIOUS_ACCOUNT", metric.metricName());
        assertEquals(1.0, metric.value());
        assertEquals("id", metric.dimensions().get(0).name());
        assertEquals(result.getId(), metric.dimensions().get(0).value());
    }

    /**
     * Tests the {@link CloudWatchMetricAlerter#init()} method.
     *
     * <p>This test verifies that the init method correctly registers the alerter with the group. It
     * calls the init method and then verifies that the registerAlerter method on the group was
     * called with the alerter instance.
     */
    @Test
    public void testInit() {
        // Verify that the alerter is registered with the group
        verify(group).registerAlerter(alerter);
    }
}

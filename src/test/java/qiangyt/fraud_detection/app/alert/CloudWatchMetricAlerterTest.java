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

public class CloudWatchMetricAlerterTest {

    @Mock CloudWatchClient client;

    @Mock GroupedAlerter group;

    @InjectMocks CloudWatchMetricAlerter alerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        alerter.init();
    }

    @Test
    public void testSend() {
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        alerter.send(result);

        var captor = ArgumentCaptor.forClass(PutMetricDataRequest.class);
        verify(client).putMetricData(captor.capture());

        var req = captor.getValue();
        assertEquals("fraud", req.namespace());
        assertEquals(1, req.metricData().size());
        var metric = req.metricData().get(0);
        assertEquals("SUSPICIOUS_ACCOUNT", metric.metricName());
        assertEquals(1.0, metric.value());
        assertEquals("id", metric.dimensions().get(0).name());
        assertEquals(result.getId(), metric.dimensions().get(0).value());
    }

    @Test
    public void testInit() {
        verify(group).registerAlerter(alerter);
    }
}

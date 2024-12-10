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
import static org.mockito.Mockito.*;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import qiangyt.fraud_detection.sdk.FraudCategory;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SqsAlerterTest {

    @Mock SqsProps props;

    @Mock SqsClient client;

    @Mock Jackson jackson;

    @Mock GroupedAlerter group;

    @InjectMocks SqsAlerter alerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(props.getAlertQueueUrl()).thenReturn("test-queue-url");
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

        when(jackson.str(result)).thenReturn("test-message");

        alerter.send(result);

        var captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(client).sendMessage(captor.capture());

        var req = captor.getValue();
        assertEquals("test-queue-url", req.queueUrl());
        assertEquals("test-message", req.messageBody());
        assertEquals("entity-id", req.messageDeduplicationId());
        assertEquals("alert", req.messageGroupId());
    }

    @Test
    public void testInit() {
        verify(group).registerAlerter(alerter);
    }
}

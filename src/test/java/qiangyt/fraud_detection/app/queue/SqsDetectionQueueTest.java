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
package qiangyt.fraud_detection.app.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/** Unit tests for {@link SqsDetectionQueue}. */
public class SqsDetectionQueueTest {

    @Mock SqsProps props;

    @Mock SqsClient client;

    @Mock Jackson jackson;

    @InjectMocks SqsDetectionQueue sqsDetectionQueue;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link SqsDetectionQueue#send(DetectionReqEntity)} method. Verifies that the
     * correct queue URL and message body are sent.
     */
    @Test
    public void testSend() {
        String queueUrl = "http://example.com/queue";
        var req = new DetectionReqEntity();
        String messageBody = "{\"key\":\"value\"}";

        // Mock the behavior of props and jackson
        when(props.getDetectQueueUrl()).thenReturn(queueUrl);
        when(jackson.str(req)).thenReturn(messageBody);

        // Call the method under test
        sqsDetectionQueue.send(req);

        // Capture the SendMessageRequest argument
        var captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(client).sendMessage(captor.capture());

        // Verify the captured request
        SendMessageRequest capturedRequest = captor.getValue();
        assertEquals(queueUrl, capturedRequest.queueUrl());
        assertEquals(messageBody, capturedRequest.messageBody());
    }
}

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
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/** Unit tests for {@link SqsDetectionDeadLetterQueue}. */
public class SqsDetectionDeadLetterQueueTest {

    @Mock SqsProps props;

    @Mock SqsClient client;

    @Mock Jackson jackson;

    @InjectMocks SqsDetectionDeadLetterQueue sqsDetectionDeadLetterQueue;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link SqsDetectionDeadLetterQueue#send(String)} method. Verifies that the correct
     * queue URL and message body are sent.
     */
    @Test
    public void testSendToDeadLetterQueue() {
        String deadLetterQueueUrl = "http://example.com/dead-letter-queue";
        String messageBody = "{\"key\":\"value\"}";

        // Mock the behavior of props
        when(props.getDetectDeadLetterQueueUrl()).thenReturn(deadLetterQueueUrl);
        when(jackson.str(messageBody)).thenReturn(messageBody);

        // Call the method under test
        sqsDetectionDeadLetterQueue.send(messageBody);

        // Capture the SendMessageRequest argument
        var captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(client).sendMessage(captor.capture());

        // Verify the captured request
        var capturedRequest = captor.getValue();
        assertEquals(deadLetterQueueUrl, capturedRequest.queueUrl());
        assertEquals(messageBody, capturedRequest.messageBody());
    }
}

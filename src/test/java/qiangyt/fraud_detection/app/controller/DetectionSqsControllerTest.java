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
package qiangyt.fraud_detection.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionApi;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Disabled
public class DetectionSqsControllerTest {

    @Mock SqsClient sqsClient;

    @Mock DetectionApi detectionApi;

    @InjectMocks DetectionSqsController detectionSqsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPoll() {
        // Mock the behavior of SqsClient and DetectionApi
        var message = Message.builder().body("{\"id\":\"123\"}").build();
        when(sqsClient.receiveMessage((ReceiveMessageRequest) any()))
                .thenReturn(ReceiveMessageResponse.builder().messages(message).build());
        when(detectionApi.detect(any())).thenReturn(null);

        detectionSqsController.poll();

        verify(detectionApi, times(1)).detect(any());
    }

    @Test
    void testPoll_noMessages() {
        // Mock the behavior of SqsClient to return no messages
        when(sqsClient.receiveMessage((ReceiveMessageRequest) any()))
                .thenReturn(ReceiveMessageResponse.builder().build());

        detectionSqsController.poll();

        verify(detectionApi, never()).detect(any());
    }

    @Test
    void testStart() {
        detectionSqsController.start();
        // Verify that the start method was called
        // verify(sqsClient, times(1)).start();
    }

    @Test
    void testStop() {
        detectionSqsController.stop();
        // Verify that the stop method was called
        // verify(sqsClient, times(1)).stop();
    }
}

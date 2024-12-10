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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.json.Jackson;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

public class DetectionSqsControllerTest {

    @Mock SqsProps props;

    @Mock SqsClient client;

    @Mock SqsProps pollingProps;

    @Mock DetectionService service;

    @InjectMocks DetectionSqsController target;

    @Mock ExecutorService sqsPollingThreadPool;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        target.setJackson(Jackson.DEFAULT);
    }

    @Test
    void testPollOne() {
        // Mock the behavior of SqsClient and DetectionApi
        var msg = Message.builder().body("{\"id\":\"123\"}").build();
        when(client.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenReturn(ReceiveMessageResponse.builder().messages(msg).build());
        when(service.detectThenAlert(any())).thenReturn(null);

        target.pollOne();

        verify(client, times(1)).deleteMessage(any(DeleteMessageRequest.class));
    }

    @Test
    void testPollOne_noMessages() {
        // Mock the behavior of SqsClient to return no messages
        when(client.receiveMessage((ReceiveMessageRequest) any()))
                .thenReturn(ReceiveMessageResponse.builder().build());

        target.pollOne();

        verify(service, never()).detectThenAlert(any());
    }

    @Test
    void testStart() {
        target.start();
        verify(sqsPollingThreadPool, times(1)).submit(any(Runnable.class));
    }

    @Test
    void testStop() {
        target.stop();
        verify(sqsPollingThreadPool, times(1)).shutdown();
    }

    @Test
    void testPoll() {
        var called = new AtomicBoolean(false);

        target =
                new DetectionSqsController() {
                    @Override
                    void pollOne() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        called.set(true);
                    }
                };
        target.setSqsPollingThreadPool(Executors.newFixedThreadPool(1));

        // Start polling in a separate thread to avoid blocking the test
        var pollingThread = new Thread(() -> target.poll());
        pollingThread.start();

        // Allow some time for the poll method to execute
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stop polling
        target.stop();

        assertTrue(called.get());
    }
}

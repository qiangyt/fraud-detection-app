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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
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
import qiangyt.fraud_detection.app.queue.SqsDetectionDeadLetterQueue;
import qiangyt.fraud_detection.app.service.DetectionService;
import qiangyt.fraud_detection.framework.json.Jackson;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

/** Unit tests for {@link DetectionSqsController}. */
public class DetectionSqsControllerTest {

    @Mock SqsProps props;

    @Mock SqsClient client;

    @Mock SqsProps pollingProps;

    @Mock DetectionService service;

    @InjectMocks DetectionSqsController target;

    @Mock ExecutorService sqsPollingThreadPool;

    @Mock SqsDetectionDeadLetterQueue deadLetterQueue;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        target.setJackson(Jackson.DEFAULT);
    }

    /** Tests the pollOne method when a message is received. */
    @Test
    void testPollOne() {
        // Mock the behavior of SqsClient and DetectionApi
        var msg = Message.builder().body("{\"id\":\"123\"}").build();
        when(client.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenReturn(ReceiveMessageResponse.builder().messages(msg).build());
        when(service.detectThenAlert(any())).thenReturn(null);

        // Call the method under test
        target.pollOne();

        // Verify that the message was deleted
        verify(client, times(1)).deleteMessage(any(DeleteMessageRequest.class));
    }

    /** Tests the pollOne method when no messages are received. */
    @Test
    void testPollOne_noMessages() {
        // Mock the behavior of SqsClient to return no messages
        when(client.receiveMessage((ReceiveMessageRequest) any()))
                .thenReturn(ReceiveMessageResponse.builder().build());

        // Call the method under test
        target.pollOne();

        // Verify that detectThenAlert was not called
        verify(service, never()).detectThenAlert(any());
    }

    /**
     * Tests the pollOne method when an error occurs and the message is sent to the dead letter
     * queue.
     */
    @Test
    void testPollOne_sendsToDeadLetterQueue() {
        // Mock the behavior of SqsClient and DetectionService
        var msg = Message.builder().body("{\"id\":\"123\"}").build();
        when(client.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenReturn(ReceiveMessageResponse.builder().messages(msg).build());
        doThrow(new RuntimeException("Processing error")).when(service).detectThenAlert(any());

        // Call the method under test
        target.pollOne();

        // Verify that the message was sent to the dead letter queue
        verify(deadLetterQueue, times(1)).send(msg.body());

        // Verify that the message was deleted from the queue
        verify(client, times(1)).deleteMessage(any(DeleteMessageRequest.class));
    }

    /** Tests the start method. */
    @Test
    void testStart() {
        // Call the method under test
        target.start();

        // Verify that the polling thread was submitted
        verify(sqsPollingThreadPool, times(1)).submit(any(Runnable.class));
    }

    /** Tests the stop method. */
    @Test
    void testStop() {
        // Call the method under test
        target.stop();

        // Verify that the polling thread pool was shut down
        verify(sqsPollingThreadPool, times(1)).shutdown();
    }

    /** Tests the poll method. */
    @Test
    void testPoll() {
        var called = new AtomicBoolean(false);

        // Override the pollOne method to simulate a delay
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

        // Verify that pollOne was called
        assertTrue(called.get());
    }

    /** Tests that poll handles IllegalStateException. */
    @Test
    void testPollHandlesIllegalStateException() {
        // Mock the behavior of SqsClient to throw an IllegalStateException
        when(client.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenThrow(new IllegalStateException("Connection pool shut down"));

        // Call the method under test
        target.poll();

        // Verify that receiveMessage was called
        verify(client, times(1)).receiveMessage(any(ReceiveMessageRequest.class));
    }

    /** Tests that poll handles other IllegalStateException. */
    @Test
    void testPollHandlesOtherIllegalStateException() throws InterruptedException {
        // Mock the behavior of SqsClient to throw an IllegalStateException
        when(client.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenThrow(new IllegalStateException("Some other error"));

        // Start polling in a separate thread to avoid blocking the test
        var thread =
                new Thread(
                        () -> {
                            target.poll();
                        });
        thread.start();

        // Allow some time for the poll method to execute
        Thread.sleep(200);
        target.getPolling().set(false);
        thread.interrupt();
        thread.join();

        // Verify that receiveMessage was called at least once
        verify(client, atLeastOnce()).receiveMessage(any(ReceiveMessageRequest.class));
    }
}

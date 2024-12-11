package qiangyt.fraud_detection.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionSqsControllerTest {

    private DetectionSqsController controller;
    private SqsProps propsMock;
    private SqsClient clientMock;
    private Jackson jacksonMock;
    private DetectionService serviceMock;
    private ExecutorService sqsPollingThreadPoolMock;
    private SqsDetectionDeadLetterQueue deadLetterQueueMock;

    @BeforeEach
    public void setUp() {
        propsMock = mock(SqsProps.class);
        clientMock = mock(SqsClient.class);
        jacksonMock = mock(Jackson.class);
        serviceMock = mock(DetectionService.class);
        sqsPollingThreadPoolMock = mock(ExecutorService.class);
        deadLetterQueueMock = mock(SqsDetectionDeadLetterQueue.class);

        controller = new DetectionSqsController();
        controller.setProps(propsMock);
        controller.setClient(clientMock);
        controller.setJackson(jacksonMock);
        controller.setService(serviceMock);
        controller.setSqsPollingThreadPool(sqsPollingThreadPoolMock);
        controller.setDeadLetterQueue(deadLetterQueueMock);
    }

    @Test
    public void testStart_HappyPath() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");

        // Act
        controller.start();

        // Assert
        verify(sqsPollingThreadPoolMock).submit(controller::poll);
    }

    @Test
    public void testStop_HappyPath() {
        // Arrange
        doNothing().when(serviceMock).detectThenAlert(any(DetectionReqEntity.class));

        // Act
        controller.stop();

        // Assert
        assertTrue(controller.getPolling().get());
        verify(clientMock, times(1)).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_Success() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().body("{\"id\":\"1\", \"amount\":100}").receiptHandle("handle1").build())
                        .build());
        doNothing().when(serviceMock).detectThenAlert(any(DetectionReqEntity.class));

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, times(1)).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_Failure() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().body("{\"id\":\"1\", \"amount\":100}").receiptHandle("handle1").build())
                        .build());
        doThrow(new RuntimeException()).when(serviceMock).detectThenAlert(any(DetectionReqEntity.class));

        // Act
        controller.pollOne();

        // Assert
        verify(deadLetterQueueMock, times(1)).send("{\"id\":\"1\", \"amount\":100}");
    }

    @Test
    public void testPollOne_EmptyQueue() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder().build());

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, never()).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_NullBody() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().receiptHandle("handle1").build())
                        .build());

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, never()).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_InvalidJsonBody() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().body("invalid json").receiptHandle("handle1").build())
                        .build());

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, never()).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_EmptyReceiptHandle() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().body("{\"id\":\"1\", \"amount\":100}").build())
                        .build());

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, never()).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }

    @Test
    public void testPollOne_NullReceiptHandle() {
        // Arrange
        when(propsMock.getDetectQueueUrl()).thenReturn("http://example.com/queue");
        when(propsMock.getBatchSize()).thenReturn(5);
        when(propsMock.getTimeout()).thenReturn(20);
        when(clientMock.receiveMessage(any(ReceiveMessageRequest.Builder.class)))
                .thenReturn(ReceiveMessageResponse.builder()
                        .messages(Message.builder().body("{\"id\":\"1\", \"amount\":100}").receiptHandle(null).build())
                        .build());

        // Act
        controller.pollOne();

        // Assert
        verify(clientMock, never()).deleteMessage(any(DeleteMessageRequest.Builder.class));
    }
}

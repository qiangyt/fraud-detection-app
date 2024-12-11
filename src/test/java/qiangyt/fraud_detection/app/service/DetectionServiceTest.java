package qiangyt.fraud_detection.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.app.alert.Alerter;
import qiangyt.fraud_detection.app.engine.DetectionEngine;
import qiangyt.fraud_detection.app.queue.DetectionRequestQueue;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReq;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DetectionServiceTest {

    @Mock
    private DetectionRequestQueue requestQueue;

    @Mock
    private DetectionEngine engine;

    @Mock
    private ThreadPoolTaskExecutor detectionTaskExecutor;

    @Mock
    private Jackson jackson;

    @Mock
    private Alerter alerter;

    @InjectMocks
    private DetectionService detectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test submitting a valid detection request.
     */
    @Test
    public void testSubmitValidRequest() {
        // Arrange
        DetectionReq req = new DetectionReq();
        DetectionReqEntity expectedEntity = req.toEntity();

        when(requestQueue.send(any(DetectionReqEntity.class))).thenReturn(expectedEntity);

        // Act
        DetectionReqEntity result = detectionService.submit(req);

        // Assert
        assertEquals(expectedEntity, result);
        verify(requestQueue).send(expectedEntity);
    }

    /**
     * Test submitting a null detection request.
     */
    @Test
    public void testSubmitNullRequest() {
        // Arrange
        DetectionReq req = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionService.submit(req));
    }

    /**
     * Test detecting fraud and sending an alert when fraud is detected.
     */
    @Test
    public void testDetectThenAlertFraudDetected() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        DetectionResult result = DetectionResult.builder()
                .entity(entity)
                .category(DetectionCategory.yes)
                .build();

        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(result);
        when(detectionTaskExecutor.submit(any(Runnable.class))).thenReturn(null);

        // Act
        CompletableFuture<DetectionResult> future = detectionService.detectThenAlert(entity);
        DetectionResult actualResult = future.join();

        // Assert
        assertEquals(result, actualResult);
        verify(engine).detect(entity);
        verify(alerter).send(result);
    }

    /**
     * Test detecting fraud and not sending an alert when no fraud is detected.
     */
    @Test
    public void testDetectThenAlertNoFraudDetected() {
        // Arrange
        DetectionReqEntity entity = new DetectionReqEntity();
        DetectionResult result = DetectionResult.builder()
                .entity(entity)
                .category(DetectionCategory.no)
                .build();

        when(engine.detect(any(DetectionReqEntity.class))).thenReturn(result);
        when(detectionTaskExecutor.submit(any(Runnable.class))).thenReturn(null);

        // Act
        CompletableFuture<DetectionResult> future = detectionService.detectThenAlert(entity);
        DetectionResult actualResult = future.join();

        // Assert
        assertEquals(result, actualResult);
        verify(engine).detect(entity);
        verify(alerter, never()).send(any(DetectionResult.class));
    }

    /**
     * Test detecting fraud with a null entity.
     */
    @Test
    public void testDetectThenAlertNullEntity() {
        // Arrange
        DetectionReqEntity entity = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> detectionService.detectThenAlert(entity));
    }

    /**
     * Test shutting down the task executor.
     */
    @Test
    public void testShutdownTaskExecutor() {
        // Arrange

        // Act
        detectionService.shutdown();

        // Assert
        verify(detectionTaskExecutor).shutdown();
    }
}

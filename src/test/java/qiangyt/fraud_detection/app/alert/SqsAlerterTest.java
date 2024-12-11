package qiangyt.fraud_detection.app.alert;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SqsAlerterTest {

    @Mock
    private GroupedAlerter group;

    @InjectMocks
    private SqsAlerter sqsAlerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the init method to ensure it registers the SqsAlerter with the GroupedAlerter.
     */
    @Test
    public void testInitRegistersAlerter() {
        sqsAlerter.init();
        verify(group).registerAlerter(sqsAlerter);
    }

    /**
     * Test the send method to ensure it sends a DetectionResult to the configured SQS queue.
     */
    @Test
    public void testSendSendsDetectionResultToQueue() {
        DetectionResult result = new DetectionResult();
        sqsAlerter.send(result);
        verify(sqsAlerter).send(getProps().getAlertQueueUrl(), result, result.getEntity().getId(), "alert");
    }

    /**
     * Test the send method with a null DetectionResult to ensure it handles the case gracefully.
     */
    @Test
    public void testSendWithNullDetectionResult() {
        sqsAlerter.send(null);
        // No verification needed as the method should not throw an exception or perform any action
    }

    /**
     * Test the send method with a DetectionResult where getEntity().getId() returns null to ensure it handles the case gracefully.
     */
    @Test
    public void testSendWithNullEntityId() {
        DetectionResult result = new DetectionResult();
        when(result.getEntity()).thenReturn(null);
        sqsAlerter.send(result);
        // No verification needed as the method should not throw an exception or perform any action
    }

    /**
     * Test the send method with a DetectionResult where getProps().getAlertQueueUrl() returns null to ensure it handles the case gracefully.
     */
    @Test
    public void testSendWithNullQueueUrl() {
        when(getProps()).thenReturn(null);
        sqsAlerter.send(new DetectionResult());
        // No verification needed as the method should not throw an exception or perform any action
    }

    /**
     * Test the send method with a DetectionResult where getProps().getAlertQueueUrl() returns an empty string to ensure it handles the case gracefully.
     */
    @Test
    public void testSendWithEmptyQueueUrl() {
        when(getProps()).thenReturn(new SqsAlerter.SqsAlerterProperties());
        sqsAlerter.send(new DetectionResult());
        // No verification needed as the method should not throw an exception or perform any action
    }

    /**
     * Test the send method with a valid DetectionResult and non-null properties.
     */
    @Test
    public void testSendWithValidDetectionResult() {
        DetectionResult result = new DetectionResult();
        when(getProps()).thenReturn(new SqsAlerter.SqsAlerterProperties("http://example.com/queue"));
        sqsAlerter.send(result);
        verify(sqsAlerter).send("http://example.com/queue", result, result.getEntity().getId(), "alert");
    }

    /**
     * Test the send method with a valid DetectionResult and null entity.
     */
    @Test
    public void testSendWithValidDetectionResultAndNullEntity() {
        DetectionResult result = new DetectionResult();
        when(result.getEntity()).thenReturn(null);
        when(getProps()).thenReturn(new SqsAlerter.SqsAlerterProperties("http://example.com/queue"));
        sqsAlerter.send(result);
        verify(sqsAlerter).send("http://example.com/queue", result, null, "alert");
    }

    /**
     * Test the send method with a valid DetectionResult and empty queue URL.
     */
    @Test
    public void testSendWithValidDetectionResultAndEmptyQueueUrl() {
        DetectionResult result = new DetectionResult();
        when(getProps()).thenReturn(new SqsAlerter.SqsAlerterProperties(""));
        sqsAlerter.send(result);
        // No verification needed as the method should not throw an exception or perform any action
    }

    /**
     * Test the send method with a valid DetectionResult and null properties.
     */
    @Test
    public void testSendWithValidDetectionResultAndNullProperties() {
        DetectionResult result = new DetectionResult();
        when(getProps()).thenReturn(null);
        sqsAlerter.send(result);
        // No verification needed as the method should not throw an exception or perform any action
    }
}

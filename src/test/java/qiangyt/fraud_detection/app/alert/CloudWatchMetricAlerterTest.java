import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CloudWatchMetricAlerterTest {

    @Mock
    private CloudWatchClient client;

    @Mock
    private GroupedAlerter group;

    @InjectMocks
    private CloudWatchMetricAlerter alerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        alerter.init();
    }

    /**
     * Test the send method with a valid DetectionResult.
     */
    @Test
    public void testSendValidDetectionResult() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);

        // Act
        alerter.send(result);

        // Assert
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test the send method with a null DetectionResult.
     */
    @Test
    public void testSendNullDetectionResult() {
        // Arrange
        DetectionResult result = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> alerter.send(result));
    }

    /**
     * Test the send method with an empty id in DetectionResult.
     */
    @Test
    public void testSendEmptyIdDetectionResult() {
        // Arrange
        DetectionResult result = new DetectionResult("", Category.FRAUD);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> alerter.send(result));
    }

    /**
     * Test the send method with an invalid category in DetectionResult.
     */
    @Test
    public void testSendInvalidCategoryDetectionResult() {
        // Arrange
        DetectionResult result = new DetectionResult("123", null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> alerter.send(result));
    }

    /**
     * Test the send method with a non-positive value in MetricDatum.
     */
    @Test
    public void testSendNonPositiveValueDetectionResult() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);
        when(client.putMetricData(any(PutMetricDataRequest.class))).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alerter.send(result));
    }

    /**
     * Test the send method with a null CloudWatchClient.
     */
    @Test
    public void testSendWithNullCloudWatchClient() {
        // Arrange
        alerter.setClient(null);
        DetectionResult result = new DetectionResult("123", Category.FRAUD);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> alerter.send(result));
    }

    /**
     * Test the send method with a valid DetectionResult and custom metric name.
     */
    @Test
    public void testSendValidDetectionResultWithCustomMetricName() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);
        alerter.setMetricName("custom_metric_name");

        // Act
        alerter.send(result);

        // Assert
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test the send method with a valid DetectionResult and custom namespace.
     */
    @Test
    public void testSendValidDetectionResultWithCustomNamespace() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);
        alerter.setNamespace("custom_namespace");

        // Act
        alerter.send(result);

        // Assert
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test the send method with a valid DetectionResult and custom dimensions.
     */
    @Test
    public void testSendValidDetectionResultWithCustomDimensions() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);
        alerter.setDimensions(new HashMap<String, String>() {{
            put("custom_dimension_key", "custom_dimension_value");
        }});

        // Act
        alerter.send(result);

        // Assert
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }

    /**
     * Test the send method with a valid DetectionResult and custom unit.
     */
    @Test
    public void testSendValidDetectionResultWithCustomUnit() {
        // Arrange
        DetectionResult result = new DetectionResult("123", Category.FRAUD);
        alerter.setUnit(MetricUnit.COUNT);

        // Act
        alerter.send(result);

        // Assert
        verify(client).putMetricData(any(PutMetricDataRequest.class));
    }
}
!!!!test_end!!!!

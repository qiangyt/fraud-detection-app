package qiangyt.fraud_detection.app.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionTaskPropsTest {

    @Test
    public void testDefaultValues() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();

        // Assert
        assertEquals(500, props.getQueueCapacity());
        assertEquals(60, props.getAwaitTerminationSeconds());
    }

    @Test
    public void testCustomQueueCapacity() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(1000);

        // Assert
        assertEquals(1000, props.getQueueCapacity());
    }

    @Test
    public void testCustomAwaitTerminationSeconds() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds(30);

        // Assert
        assertEquals(30, props.getAwaitTerminationSeconds());
    }

    @Test
    public void testNegativeQueueCapacity() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(-1);

        // Assert
        assertEquals(500, props.getQueueCapacity()); // Default value should be used
    }

    @Test
    public void testZeroQueueCapacity() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(0);

        // Assert
        assertEquals(500, props.getQueueCapacity()); // Default value should be used
    }

    @Test
    public void testNegativeAwaitTerminationSeconds() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds(-1);

        // Assert
        assertEquals(60, props.getAwaitTerminationSeconds()); // Default value should be used
    }

    @Test
    public void testZeroAwaitTerminationSeconds() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds(0);

        // Assert
        assertEquals(60, props.getAwaitTerminationSeconds()); // Default value should be used
    }

    @Test
    public void testNullQueueCapacity() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity(null);

        // Assert
        assertEquals(500, props.getQueueCapacity()); // Default value should be used
    }

    @Test
    public void testNullAwaitTerminationSeconds() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds(null);

        // Assert
        assertEquals(60, props.getAwaitTerminationSeconds()); // Default value should be used
    }

    @Test
    public void testEmptyQueueCapacity() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setQueueCapacity("");

        // Assert
        assertEquals(500, props.getQueueCapacity()); // Default value should be used
    }

    @Test
    public void testEmptyAwaitTerminationSeconds() {
        // Arrange
        DetectionTaskProps props = new DetectionTaskProps();
        props.setAwaitTerminationSeconds("");

        // Assert
        assertEquals(60, props.getAwaitTerminationSeconds()); // Default value should be used
    }
}

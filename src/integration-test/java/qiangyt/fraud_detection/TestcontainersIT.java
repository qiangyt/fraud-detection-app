!!!!test_begin!!!!

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestcontainersITTest {

    @BeforeEach
    public void setUp() {
        // Initialize the LocalStackContainer before each test
        TestcontainersIT.localstack.start();
    }

    @Test
    public void testLocalstackStartedSuccessfully() {
        // Check if the LocalStack container is running
        assertTrue(TestcontainersIT.localstack.isRunning());
    }

    @Test
    public void testServicesAreAvailable() {
        // Verify that the required services are available
        assertTrue(TestcontainersIT.localstack.getService(Service.SQS).isRunning());
        assertTrue(TestcontainersIT.localstack.getService(Service.CLOUDWATCHLOGS).isRunning());
        assertTrue(TestcontainersIT.localstack.getService(LocalStackContainer.EnabledService.named("events")).isRunning());
    }

    @Test
    public void testLocalstackStoppedAfterTest() {
        // Stop the LocalStack container after the test
        TestcontainersIT.localstack.stop();
        assertFalse(TestcontainersIT.localstack.isRunning());
    }
}
!!!!test_end!!!!

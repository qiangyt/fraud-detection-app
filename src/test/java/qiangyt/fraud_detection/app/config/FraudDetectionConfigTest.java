package qiangyt.fraud_detection.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FraudDetectionConfigTest {

    @Autowired
    private ApplicationContext context;

    /**
     * Test to verify that the OpenAPI bean is created correctly.
     */
    @Test
    public void testOpenApiInfo() {
        // Arrange
        OpenAPI openApi = context.getBean(OpenAPI.class);

        // Act & Assert
        assertThat(openApi).isNotNull();
        assertThat(openApi.getInfo()).isNotNull();
        assertThat(openApi.getInfo().getTitle()).isEqualTo("Fraud Detection API");
        assertThat(openApi.getInfo().getVersion()).isEqualTo("1.0");
    }

    /**
     * Test to verify that the ThreadPoolTaskExecutor bean is created correctly.
     */
    @Test
    public void testDetectionTaskExecutor() {
        // Arrange
        ThreadPoolTaskExecutor executor = context.getBean(ThreadPoolTaskExecutor.class);

        // Act & Assert
        assertThat(executor).isNotNull();
        assertThat(executor.getCorePoolSize()).isEqualTo(Runtime.getRuntime().availableProcessors());
        assertThat(executor.getMaxPoolSize()).isEqualTo(Runtime.getRuntime().availableProcessors());
        assertThat(executor.getQueueCapacity()).isEqualTo(10); // Assuming default value for queue capacity
        assertThat(executor.getThreadNamePrefix()).isEqualTo("detectionTask-");
        assertThat(executor.isWaitForTasksToCompleteOnShutdown()).isTrue();
        assertThat(executor.getAwaitTerminationSeconds()).isEqualTo(60); // Assuming default value for await termination seconds
    }

    /**
     * Test to verify that the ExecutorService bean is created correctly.
     */
    @Test
    public void testSqsPollingThreadPool() {
        // Arrange
        ExecutorService executor = context.getBean(ExecutorService.class);

        // Act & Assert
        assertThat(executor).isNotNull();
        assertThat(((ThreadPoolTaskExecutor) executor).getCorePoolSize()).isEqualTo(5); // Assuming default value for thread pool size
    }

    /**
     * Test to verify that the DetectionTaskProps bean is injected correctly.
     */
    @Test
    public void testDetectionTaskProps() {
        // Arrange
        DetectionTaskProps props = context.getBean(DetectionTaskProps.class);

        // Act & Assert
        assertThat(props).isNotNull();
        assertThat(props.getQueueCapacity()).isEqualTo(10); // Assuming default value for queue capacity
        assertThat(props.getAwaitTerminationSeconds()).isEqualTo(60); // Assuming default value for await termination seconds
    }

    /**
     * Test to verify that the SqsProps bean is injected correctly.
     */
    @Test
    public void testSqsProps() {
        // Arrange
        SqsProps props = context.getBean(SqsProps.class);

        // Act & Assert
        assertThat(props).isNotNull();
        assertThat(props.getThreadPoolSize()).isEqualTo(5); // Assuming default value for thread pool size
    }
}

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
package qiangyt.fraud_detection.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import qiangyt.fraud_detection.framework.aws.cloudwatch.CloudWatchConfig;
import qiangyt.fraud_detection.framework.aws.sqs.SqsConfig;
import qiangyt.fraud_detection.framework.rest.RestConfig;

/**
 * Configuration class for the Fraud Detection application. It sets up various beans required for
 * the application.
 */
@Configuration
@EnableAsync
@Import({
    DetectionTaskProps.class,
    RuleProps.class,
    SqsProps.class,
    AwsProps.class,
    SqsConfig.class,
    CloudWatchConfig.class,
    RestConfig.class
})
public class FraudDetectionConfig {

    /**
     * Creates an OpenAPI bean to provide API documentation.
     *
     * @return an OpenAPI instance with API information.
     */
    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(new Info().title("Fraud Detection API").version("1.0"));
    }

    /**
     * Creates a ThreadPoolTaskExecutor bean for handling detection tasks.
     *
     * @param props the properties for configuring the task executor.
     * @return a configured ThreadPoolTaskExecutor instance.
     */
    @Bean
    public ThreadPoolTaskExecutor detectionTaskExecutor(DetectionTaskProps props) {
        int cpuCoreCount = Runtime.getRuntime().availableProcessors();

        var p = new ThreadPoolTaskExecutor();
        p.setCorePoolSize(cpuCoreCount);
        p.setMaxPoolSize(cpuCoreCount);
        p.setQueueCapacity(props.getQueueCapacity());
        p.setThreadNamePrefix("detectionTask-");
        p.setWaitForTasksToCompleteOnShutdown(true);
        p.setAwaitTerminationSeconds(props.getAwaitTerminationSeconds());

        return p;
    }

    /**
     * Creates an ExecutorService bean for handling SQS polling tasks.
     *
     * @param props the properties for configuring the thread pool.
     * @return a configured ExecutorService instance.
     */
    @Bean
    public ExecutorService sqsPollingThreadPool(SqsProps props) {
        return Executors.newFixedThreadPool(props.getThreadPoolSize());
    }
}

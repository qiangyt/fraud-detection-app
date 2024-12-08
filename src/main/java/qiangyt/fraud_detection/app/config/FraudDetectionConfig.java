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

@Configuration
@EnableAsync
@Import({
    DetectionTaskProps.class,
    RuleProps.class,
    SqsPollingProps.class,
    AwsProps.class,
    SqsConfig.class,
    CloudWatchConfig.class,
    RestConfig.class
})
public class FraudDetectionConfig {

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(new Info().title("Fraud Detection API").version("1.0"));
    }

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

    @Bean
    public ExecutorService sqsPollingThreadPool(SqsPollingProps props) {
        return Executors.newFixedThreadPool(props.getThreadPoolSize());
    }
}

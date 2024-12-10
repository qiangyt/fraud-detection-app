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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "app.sqs")
public class SqsProps {

    /** The size of the thread pool used for processing SQS messages. */
    int threadPoolSize = 1;

    /** The number of messages to retrieve in a single batch from the SQS queue. */
    int batchSize = 10;

    /** The timeout (in seconds) for processing messages from the SQS queue. */
    int timeout = 20;

    /**
     * The URL of the SQS queue used for fraud detection. This value is retrieved from the
     * environment variable AWS_SQS_DETECT_QUEUE_URL.
     *
     * <p>For example, "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection"
     */
    String detectQueueUrl = System.getenv("AWS_SQS_DETECT_QUEUE_URL");

    /**
     * The URL of the SQS queue used for fraud alerts. It must be a FIFO queue to ensure that
     * messages are processed in order and uniqueness.
     *
     * <p>This value is retrieved from the environment variable AWS_SQS_ALERT_QUEUE_URL.
     *
     * <p>for example, "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_alert.fifo"
     */
    String alertQueueUrl = System.getenv("AWS_SQS_ALERT_QUEUE_URL");
}

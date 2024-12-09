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

    int threadPoolSize = 1;

    int batchSize = 10;

    int timeout = 20;

    String detectQueueUrl = System.getenv("AWS_SQS_DETECT_QUEUE_URL"); //
    // "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection";

    String alertQueueUrl =
            System.getenv(
                    "AWS_SQS_ALERT_QUEUE_URL"); // "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_alert.fifo";
}

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
package qiangyt.fraud_detection.framework.aws.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/** Configuration class for setting up AWS SQS client. */
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
@Configuration
public class SqsConfig {

    @Autowired private AwsProps props;

    /**
     * Retrieves the AWS region from the properties.
     *
     * @return the AWS region.
     */
    @Bean
    public Region getRegion() {
        return Region.of(props.getRegion());
    }

    /**
     * Creates and configures an SQS client using the provided region and credentials.
     *
     * @param region the AWS region.
     * @return the configured SQS client.
     */
    @Bean
    public SqsClient createClient(Region region) {
        var p = getProps();
        var credential =
                StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(p.getAccessKeyId(), p.getAccessKeySecret()));
        return SqsClient.builder().region(region).credentialsProvider(credential).build();
    }
}

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/** Unit tests for {@link SqsConfig}. */
public class SqsConfigTest {

    private AwsProps awsProps;
    private SqsConfig sqsConfig;

    /** Sets up the test environment before each test. */
    @BeforeEach
    public void setUp() {
        // Mocking AwsProps
        awsProps = Mockito.mock(AwsProps.class);
        when(awsProps.getRegion()).thenReturn("us-west-2");
        when(awsProps.getAccessKeyId()).thenReturn("testAccessKeyId");
        when(awsProps.getAccessKeySecret()).thenReturn("testAccessKeySecret");

        // Initializing SqsConfig with mocked AwsProps
        sqsConfig = new SqsConfig();
        sqsConfig.setProps(awsProps);
    }

    /** Tests the {@link SqsConfig#getRegion()} method. */
    @Test
    public void testGetRegion() {
        // Retrieve the region from SqsConfig
        Region region = sqsConfig.getRegion();

        // Assert that the region is not null and matches the expected value
        assertNotNull(region);
        assert (region.id().equals("us-west-2"));
    }

    /** Tests the {@link SqsConfig#createClient(Region)} method. */
    @Test
    public void testCreateClient() {
        // Retrieve the region from SqsConfig
        Region region = sqsConfig.getRegion();

        // Create an SqsClient using the retrieved region
        SqsClient sqsClient = sqsConfig.createClient(region);

        // Assert that the SqsClient is not null
        assertNotNull(sqsClient);
    }
}

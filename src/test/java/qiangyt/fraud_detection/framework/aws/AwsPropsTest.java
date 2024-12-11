!!!!test_begin!!!!

package qiangyt.fraud_detection.framework.aws;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AwsPropsTest {

    private AwsProps awsProps;

    @BeforeEach
    public void setUp() {
        awsProps = new AwsProps();
    }

    /**
     * Test case to verify that the region is correctly set from environment variable.
     */
    @Test
    public void testRegionFromEnvVariable() {
        // Set environment variable for AWS region
        System.setProperty("AWS_REGION", "us-east-1");

        // Create AwsProps instance
        awsProps = new AwsProps();

        // Verify that the region is correctly set
        assertEquals("us-east-1", awsProps.getRegion());
    }

    /**
     * Test case to verify that the access key ID is correctly set from environment variable.
     */
    @Test
    public void testAccessKeyIdFromEnvVariable() {
        // Set environment variable for AWS access key ID
        System.setProperty("AWS_ACCESS_KEY_ID", "AKIAIOSFODNN7EXAMPLE");

        // Create AwsProps instance
        awsProps = new AwsProps();

        // Verify that the access key ID is correctly set
        assertEquals("AKIAIOSFODNN7EXAMPLE", awsProps.getAccessKeyId());
    }

    /**
     * Test case to verify that the access key secret is correctly set from environment variable.
     */
    @Test
    public void testAccessKeySecretFromEnvVariable() {
        // Set environment variable for AWS access key secret
        System.setProperty("AWS_ACCESS_KEY_SECRET", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");

        // Create AwsProps instance
        awsProps = new AwsProps();

        // Verify that the access key secret is correctly set
        assertEquals("wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY", awsProps.getAccessKeySecret());
    }

    /**
     * Test case to verify that default values are used when environment variables are not set.
     */
    @Test
    public void testDefaultValuesWhenEnvVariablesNotSet() {
        // Clear environment variables for AWS region, access key ID, and access key secret
        System.clearProperty("AWS_REGION");
        System.clearProperty("AWS_ACCESS_KEY_ID");
        System.clearProperty("AWS_ACCESS_KEY_SECRET");

        // Create AwsProps instance
        awsProps = new AwsProps();

        // Verify that default values are used
        assertNull(awsProps.getRegion());
        assertNull(awsProps.getAccessKeyId());
        assertNull(awsProps.getAccessKeySecret());
    }
}
!!!!test_end!!!!

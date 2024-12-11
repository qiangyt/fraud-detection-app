package qiangyt.fraud_detection.framework.aws.sqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SqsConfigTest {

    @Autowired
    private ApplicationContext context;

    private SqsConfig sqsConfig;

    @BeforeEach
    public void setUp() {
        sqsConfig = new SqsConfig();
        AwsProps awsProps = new AwsProps();
        awsProps.setRegion("us-east-1");
        awsProps.setAccessKeyId("accessKey");
        awsProps.setAccessKeySecret("secretKey");
        sqsConfig.setProps(awsProps);
    }

    @Test
    public void testGetRegion_happyPath() {
        // Arrange
        Region expectedRegion = Region.of("us-east-1");

        // Act
        Region actualRegion = sqsConfig.getRegion();

        // Assert
        assertEquals(expectedRegion, actualRegion);
    }

    @Test
    public void testCreateClient_withValidCredentials_happyPath() {
        // Arrange
        Region region = Region.of("us-east-1");
        SqsClient expectedClient = mock(SqsClient.class);

        when(sqsConfig.createClient(region)).thenReturn(expectedClient);

        // Act
        SqsClient actualClient = sqsConfig.createClient(region);

        // Assert
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testCreateClient_withInvalidCredentials_negativePath() {
        // Arrange
        Region region = Region.of("us-east-1");
        AwsProps awsProps = new AwsProps();
        awsProps.setRegion("us-east-1");
        awsProps.setAccessKeyId(null);
        awsProps.setAccessKeySecret(null);
        sqsConfig.setProps(awsProps);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sqsConfig.createClient(region));
    }

    @Test
    public void testCreateClient_withEmptyCredentials_negativePath() {
        // Arrange
        Region region = Region.of("us-east-1");
        AwsProps awsProps = new AwsProps();
        awsProps.setRegion("us-east-1");
        awsProps.setAccessKeyId("");
        awsProps.setAccessKeySecret("");
        sqsConfig.setProps(awsProps);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sqsConfig.createClient(region));
    }
}

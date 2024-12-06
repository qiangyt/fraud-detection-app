package qiangyt.fraud_detection.framework.aws.sqs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import qiangyt.fraud_detection.framework.aws.AwsProps;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

public class SqsConfigTest {

    private AwsProps awsProps;
    private SqsConfig sqsConfig;

    @BeforeEach
    public void setUp() {
        awsProps = Mockito.mock(AwsProps.class);
        when(awsProps.getRegion()).thenReturn("us-west-2");
        when(awsProps.getAccessKeyId()).thenReturn("testAccessKeyId");
        when(awsProps.getAccessKeySecret()).thenReturn("testAccessKeySecret");

        sqsConfig = new SqsConfig();
        sqsConfig.setProps(awsProps);
    }

    @Test
    public void testGetRegion() {
        Region region = sqsConfig.getRegion();
        assertNotNull(region);
        assert (region.id().equals("us-west-2"));
    }

    @Test
    public void testCreateClient() {
        Region region = sqsConfig.getRegion();
        SqsClient sqsClient = sqsConfig.createClient(region);
        assertNotNull(sqsClient);
    }
}

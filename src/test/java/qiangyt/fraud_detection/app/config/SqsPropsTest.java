!!!!test_begin!!!!

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SqsPropsTest {

    @Test
    public void testDefaultValues() {
        // Arrange
        SqsProps sqsProps = new SqsProps();

        // Assert
        assertEquals(1, sqsProps.getThreadPoolSize());
        assertEquals(10, sqsProps.getBatchSize());
        assertEquals(20, sqsProps.getTimeout());
        assertNull(sqsProps.getDetectQueueUrl());
        assertNull(sqsProps.getDetectDeadLetterQueueUrl());
        assertNull(sqsProps.getAlertQueueUrl());
    }

    @Test
    public void testCustomValues() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setThreadPoolSize(5);
        sqsProps.setBatchSize(20);
        sqsProps.setTimeout(30);

        // Assert
        assertEquals(5, sqsProps.getThreadPoolSize());
        assertEquals(20, sqsProps.getBatchSize());
        assertEquals(30, sqsProps.getTimeout());
    }

    @Test
    public void testEnvironmentVariableValues() {
        // Arrange
        System.setProperty("AWS_SQS_DETECT_QUEUE_URL", "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection");
        System.setProperty("AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL", "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection_dead_letter");
        System.setProperty("AWS_SQS_ALERT_QUEUE_URL", "https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_alert.fifo");

        SqsProps sqsProps = new SqsProps();

        // Assert
        assertEquals("https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection", sqsProps.getDetectQueueUrl());
        assertEquals("https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection_dead_letter", sqsProps.getDetectDeadLetterQueueUrl());
        assertEquals("https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_alert.fifo", sqsProps.getAlertQueueUrl());
    }

    @Test
    public void testNullEnvironmentVariableValues() {
        // Arrange
        System.clearProperty("AWS_SQS_DETECT_QUEUE_URL");
        System.clearProperty("AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL");
        System.clearProperty("AWS_SQS_ALERT_QUEUE_URL");

        SqsProps sqsProps = new SqsProps();

        // Assert
        assertNull(sqsProps.getDetectQueueUrl());
        assertNull(sqsProps.getDetectDeadLetterQueueUrl());
        assertNull(sqsProps.getAlertQueueUrl());
    }

    @Test
    public void testEmptyEnvironmentVariableValues() {
        // Arrange
        System.setProperty("AWS_SQS_DETECT_QUEUE_URL", "");
        System.setProperty("AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL", "");
        System.setProperty("AWS_SQS_ALERT_QUEUE_URL", "");

        SqsProps sqsProps = new SqsProps();

        // Assert
        assertEquals("", sqsProps.getDetectQueueUrl());
        assertEquals("", sqsProps.getDetectDeadLetterQueueUrl());
        assertEquals("", sqsProps.getAlertQueueUrl());
    }

    @Test
    public void testNegativeThreadPoolSize() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setThreadPoolSize(-1);

        // Assert
        assertEquals(1, sqsProps.getThreadPoolSize());
    }

    @Test
    public void testZeroThreadPoolSize() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setThreadPoolSize(0);

        // Assert
        assertEquals(1, sqsProps.getThreadPoolSize());
    }

    @Test
    public void testNegativeBatchSize() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setBatchSize(-1);

        // Assert
        assertEquals(10, sqsProps.getBatchSize());
    }

    @Test
    public void testZeroBatchSize() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setBatchSize(0);

        // Assert
        assertEquals(10, sqsProps.getBatchSize());
    }

    @Test
    public void testNegativeTimeout() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setTimeout(-1);

        // Assert
        assertEquals(20, sqsProps.getTimeout());
    }

    @Test
    public void testZeroTimeout() {
        // Arrange
        SqsProps sqsProps = new SqsProps();
        sqsProps.setTimeout(0);

        // Assert
        assertEquals(20, sqsProps.getTimeout());
    }
}

!!!!test_end!!!!
<|endoftext|>As an AI language model, I can provide you with the code for testing the `SqsProps` class. The code includes various test cases to ensure that the class behaves as expected under different conditions. Here is a brief explanation of each test case:

  * `testCustomValues`: This test case sets custom values for the `threadPoolSize`, `batchSize`, and `timeout` properties and asserts that they are set correctly.
  * `testEnvironmentVariableValues`: This test case sets environment variables for the `detectQueueUrl`, `detectDeadLetterQueueUrl`, and `alertQueueUrl` properties and asserts that they are set correctly.
  * `testNullEnvironmentVariableValues`: This test case clears the environment variables for the `detectQueueUrl`, `detectDeadLetterQueueUrl`, and `alertQueueUrl` properties and asserts that they are null.
  * `testEmptyEnvironmentVariableValues`: This test case sets empty strings as environment variables for the `detectQueueUrl`, `detectDeadLetterQueueUrl`, and `alertQueueUrl` properties and asserts that they are empty strings.
  * `testNegativeThreadPoolSize`: This test case sets a negative value for the `threadPoolSize` property and asserts that it is set to the default value of 1.
  * `testZeroThreadPoolSize`: This test case sets a zero value for the `threadPoolSize` property and asserts that it is set to the default value of 1.
  * `testNegativeBatchSize`: This test case sets a negative value for the `batchSize` property and asserts that it is set to the default value of 10.
  * `testZeroBatchSize`: This test case sets a zero value for the `batchSize` property and asserts that it is set to the default value of 10.
  * `testNegativeTimeout`: This test case sets a negative value for the `timeout` property and asserts that it is set to the default value of 20.
  * `testZeroTimeout`: This test case sets a zero value for the `timeout` property and asserts that it is set to the default value of 20.

These test cases cover various scenarios, including custom values, environment variables, null values, empty strings, and negative or zero values. By running these tests, you can ensure that the `SqsProps` class behaves as expected under different conditions.

package qiangyt.fraud_detection.app;

import org.junit.jupiter.api.Test;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class FraudDetectionAppTest {

    /**
     * Test case to verify that the main method runs successfully with valid arguments.
     */
    @Test
    public void testMainMethodWithValidArguments() {
        // Arrange
        String[] args = {"validArgument"};

        // Act
        FraudDetectionApp.main(args);

        // Assert (no assertions needed as the application should run without throwing exceptions)
    }

    /**
     * Test case to verify that the main method throws an exception when null arguments are passed.
     */
    @Test
    public void testMainMethodWithNullArguments() {
        // Arrange
        String[] args = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> FraudDetectionApp.main(args));
    }

    /**
     * Test case to verify that the main method throws an exception when empty arguments are passed.
     */
    @Test
    public void testMainMethodWithEmptyArguments() {
        // Arrange
        String[] args = new String[0];

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> FraudDetectionApp.main(args));
    }

    /**
     * Test case to verify that the main method throws an exception when invalid arguments are passed.
     */
    @Test
    public void testMainMethodWithInvalidArguments() {
        // Arrange
        String[] args = {"invalidArgument"};

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> FraudDetectionApp.main(args));
    }
}

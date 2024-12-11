package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionHelperTest {

    /**
     * Test the getRootCause method with a simple exception.
     */
    @Test
    public void testGetRootCauseSimpleException() {
        // Arrange
        Throwable error = new RuntimeException("Simple exception");

        // Act
        Throwable rootCause = ExceptionHelper.getRootCause(error);

        // Assert
        assertEquals(error, rootCause);
    }

    /**
     * Test the getRootCause method with an InvocationTargetException.
     */
    @Test
    public void testGetRootCauseInvocationTargetException() {
        // Arrange
        Throwable targetException = new RuntimeException("Target exception");
        Throwable error = new InvocationTargetException(targetException);

        // Act
        Throwable rootCause = ExceptionHelper.getRootCause(error);

        // Assert
        assertEquals(targetException, rootCause);
    }

    /**
     * Test the topElement method with a simple exception.
     */
    @Test
    public void testTopElementSimpleException() {
        // Arrange
        Throwable error = new RuntimeException("Simple exception");
        StackTraceElement expectedElement = error.getStackTrace()[0];

        // Act
        StackTraceElement element = ExceptionHelper.topElement(error);

        // Assert
        assertEquals(expectedElement, element);
    }

    /**
     * Test the topElement method with an empty stack trace.
     */
    @Test
    public void testTopElementEmptyStackTrace() {
        // Arrange
        Throwable error = new RuntimeException("Simple exception") {{
            setStackTrace(new StackTraceElement[0]);
        }};
        StackTraceElement expectedElement = null;

        // Act
        StackTraceElement element = ExceptionHelper.topElement(error);

        // Assert
        assertNull(element);
    }
}

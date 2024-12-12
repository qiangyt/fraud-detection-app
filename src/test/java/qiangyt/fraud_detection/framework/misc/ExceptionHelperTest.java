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
package qiangyt.fraud_detection.framework.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

public class ExceptionHelperTest {

    /** Test the getRootCause method with a simple exception. */
    @Test
    public void testGetRootCauseSimpleException() {
        // Arrange
        var error = new RuntimeException("Simple exception");

        // Act
        var rootCause = ExceptionHelper.getRootCause(error);

        // Assert
        assertEquals(error, rootCause);
    }

    /** Test the getRootCause method with an InvocationTargetException. */
    @Test
    public void testGetRootCauseInvocationTargetException() {
        // Arrange
        var targetException = new RuntimeException("Target exception");
        var error = new InvocationTargetException(targetException);

        // Act
        var rootCause = ExceptionHelper.getRootCause(error);

        // Assert
        assertEquals(targetException, rootCause);
    }

    /** Test the topElement method with a simple exception. */
    @Test
    public void testTopElementSimpleException() {
        // Arrange
        var error = new RuntimeException("Simple exception");
        var expectedElement = error.getStackTrace()[0];

        // Act
        var element = ExceptionHelper.topElement(error);

        // Assert
        assertEquals(expectedElement, element);
    }

    /** Test the topElement method with an empty stack trace. */
    @Test
    public void testTopElementEmptyStackTrace() {
        // Arrange
        var error =
                new RuntimeException("Simple exception") {
                    {
                        setStackTrace(new StackTraceElement[0]);
                    }
                };

        // Act
        var element = ExceptionHelper.topElement(error);

        // Assert
        assertNull(element);
    }
}

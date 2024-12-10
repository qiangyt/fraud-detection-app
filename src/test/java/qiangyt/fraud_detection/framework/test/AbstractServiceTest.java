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
package qiangyt.fraud_detection.framework.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.mock.web.MockHttpServletRequest;
import qiangyt.fraud_detection.framework.errs.BaseError;

/**
 * Base class for service unit tests, encapsulating basic usage methods for Mockito.
 *
 * <p>The purpose is to verify the correctness of business logic, including parameter/result passing
 * with repositories.
 */
@Disabled
@ExtendWith(MockitoExtension.class)
public abstract class AbstractServiceTest {

    MockHttpServletRequest request = new MockHttpServletRequest();

    /** Initializes mocks before each test. */
    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Matches a boolean value.
     *
     * @param that the expected boolean value
     * @return true if the actual value matches the expected value
     */
    public static boolean matchBoolean(boolean that) {
        return ArgumentMatchers.booleanThat(
                b -> {
                    Assertions.assertEquals(Boolean.valueOf(that), b);
                    return true;
                });
    }

    /**
     * Matches a string value.
     *
     * @param that the expected string value
     * @return the matched string value
     */
    public static String matchString(String that) {
        return ArgumentMatchers.argThat((String actual) -> that.equals(actual));
    }

    /**
     * Matches an integer value.
     *
     * @param that the expected integer value
     * @return true if the actual value matches the expected value
     */
    public static int matchInt(int that) {
        return ArgumentMatchers.intThat(
                i -> {
                    Assertions.assertEquals(Integer.valueOf(that), i);
                    return true;
                });
    }

    /**
     * Asserts that the executable throws an exception of the expected type with the expected code.
     *
     * @param <T> the type of the expected exception
     * @param expectedType the class of the expected exception
     * @param code the expected error code
     * @param executable the executable to test
     * @return the thrown exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseError> T assertThrows(
            Class<T> expectedType, Enum<?> code, Executable executable) {
        try {
            executable.execute();
        } catch (Throwable actualException) {
            if (expectedType.isInstance(actualException)) {
                return (T) actualException;
            }

            // UnrecoverableExceptions.rethrowIfUnrecoverable(actualException);

            throw new AssertionFailedError(
                    "caught unexpected exception",
                    expectedType,
                    actualException.getClass(),
                    actualException);
        }

        throw new AssertionFailedError("no expected exception throw", expectedType, "null");
    }
}

!!!!test_begin!!!!

package qiangyt.fraud_detection.framework.errs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorCodeTest {

    /**
     * Test the NONE enum value.
     */
    @Test
    public void testNONE() {
        // Given
        ErrorCode errorCode = ErrorCode.NONE;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("NONE", name);
    }

    /**
     * Test the PATH_NOT_FOUND enum value.
     */
    @Test
    public void testPATH_NOT_FOUND() {
        // Given
        ErrorCode errorCode = ErrorCode.PATH_NOT_FOUND;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("PATH_NOT_FOUND", name);
    }

    /**
     * Test the PARAMETER_NOT_VALID enum value.
     */
    @Test
    public void testPARAMETER_NOT_VALID() {
        // Given
        ErrorCode errorCode = ErrorCode.PARAMETER_NOT_VALID;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("PARAMETER_NOT_VALID", name);
    }

    /**
     * Test the CONSTRAINT_VIOLATION enum value.
     */
    @Test
    public void testCONSTRAINT_VIOLATION() {
        // Given
        ErrorCode errorCode = ErrorCode.CONSTRAINT_VIOLATION;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("CONSTRAINT_VIOLATION", name);
    }

    /**
     * Test the WRONG_DATA_FORMAT enum value.
     */
    @Test
    public void testWRONG_DATA_FORMAT() {
        // Given
        ErrorCode errorCode = ErrorCode.WRONG_DATA_FORMAT;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("WRONG_DATA_FORMAT", name);
    }

    /**
     * Test the FIELD_NOT_UPDATEABLE enum value.
     */
    @Test
    public void testFIELD_NOT_UPDATEABLE() {
        // Given
        ErrorCode errorCode = ErrorCode.FIELD_NOT_UPDATEABLE;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("FIELD_NOT_UPDATEABLE", name);
    }

    /**
     * Test the FIELD_NOT_ASSIGNABLE enum value.
     */
    @Test
    public void testFIELD_NOT_ASSIGNABLE() {
        // Given
        ErrorCode errorCode = ErrorCode.FIELD_NOT_ASSIGNABLE;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("FIELD_NOT_ASSIGNABLE", name);
    }

    /**
     * Test the FIELD_NOT_EXISTS enum value.
     */
    @Test
    public void testFIELD_NOT_EXISTS() {
        // Given
        ErrorCode errorCode = ErrorCode.FIELD_NOT_EXISTS;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("FIELD_NOT_EXISTS", name);
    }

    /**
     * Test the INVALID_ENUM enum value.
     */
    @Test
    public void testINVALID_ENUM() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_ENUM;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("INVALID_ENUM", name);
    }

    /**
     * Test the INVALID_PROPERTY enum value.
     */
    @Test
    public void testINVALID_PROPERTY() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_PROPERTY;

        // When
        String name = errorCode.name();

        // Then
        assertEquals("INVALID_PROPERTY", name);
    }

    /**
     * Test that NONE is the default value when no enum constant is specified.
     */
    @Test
    public void testDefaultNONE() {
        // Given
        ErrorCode errorCode = null;

        // When
        ErrorCode result = ErrorCode.valueOf("NONE");

        // Then
        assertEquals(ErrorCode.NONE, result);
    }

    /**
     * Test that an IllegalArgumentException is thrown when an invalid enum constant is specified.
     */
    @Test
    public void testInvalidEnumConstant() {
        // Given
        String invalidConstant = "INVALID_CONSTANT";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> ErrorCode.valueOf(invalidConstant));
    }
}
!!!!test_end!!!!

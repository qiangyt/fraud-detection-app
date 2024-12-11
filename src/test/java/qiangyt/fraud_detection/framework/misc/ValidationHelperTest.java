!!!!test_begin!!!!

package qiangyt.fraud_detection.framework.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationHelperTest {

    @Test
    public void testHackBean() {
        // Arrange
        ObjectError err = new FieldError("bean", "field", null, false, null, null, null);

        // Act
        Object result = ValidationHelper.hackBean(err);

        // Assert
        assertNull(result);
    }

    @Test
    public void testHackBeanClass() {
        // Arrange
        ObjectError err = new FieldError("bean", "field", null, false, null, null, null);

        // Act
        Class<?> result = ValidationHelper.hackBeanClass(err);

        // Assert
        assertNull(result);
    }

    @Test
    public void testHackJsonFieldName_noJsonProperty() {
        // Arrange
        FieldError err = new FieldError("bean", "field", null, false, null, null, null);

        // Act
        String result = ValidationHelper.hackJsonFieldName(err);

        // Assert
        assertEquals("field", result);
    }

    @Test
    public void testHackJsonFieldName_withJsonProperty() {
        // Arrange
        FieldError err = new FieldError("bean", "field", null, false, null, null, null);
        Field field = getField(BeanWithJsonProperty.class, "field");
        field.setAnnotation(new JsonProperty("jsonField"));

        // Act
        String result = ValidationHelper.hackJsonFieldName(err);

        // Assert
        assertEquals("jsonField", result);
    }

    @Test
    public void testHackJsonFieldName_withEmptyJsonProperty() {
        // Arrange
        FieldError err = new FieldError("bean", "field", null, false, null, null, null);
        Field field = getField(BeanWithEmptyJsonProperty.class, "field");
        field.setAnnotation(new JsonProperty "");

        // Act
        String result = ValidationHelper.hackJsonFieldName(err);

        // Assert
        assertEquals("field", result);
    }

    @Test
    public void testHackJsonFieldName_withNullField() {
        // Arrange
        ObjectError err = new FieldError(null, null, null, false, null, null, null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> ValidationHelper.hackJsonFieldName(err));
    }

    @Test
    public void testHackJsonFieldName_withNoSuchFieldException() {
        // Arrange
        ObjectError err = new FieldError("bean", "nonExistentField", null, false, null, null, null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> ValidationHelper.hackJsonFieldName(err));
    }

    private Field getField(Class<?> klass, String fieldName) {
        try {
            return klass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonProperty("jsonField")
    static class BeanWithJsonProperty {
        public String field;
    }

    @JsonProperty("")
    static class BeanWithEmptyJsonProperty {
        public String field;
    }
}
!!!!test_end!!!!

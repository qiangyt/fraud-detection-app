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

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/** Helper class for validation-related utilities. */
public class ValidationHelper {

    /**
     * Unwraps the ConstraintViolation from the given ObjectError and returns the leaf bean.
     *
     * @param err the ObjectError to unwrap
     * @return the leaf bean or null if not found
     */
    public static Object hackBean(ObjectError err) {
        var src = err.unwrap(ConstraintViolation.class);
        return (src == null) ? null : src.getLeafBean();
    }

    /**
     * Retrieves the class of the leaf bean from the given ObjectError.
     *
     * @param err the ObjectError to inspect
     * @return the class of the leaf bean or null if not found
     */
    public static Class<?> hackBeanClass(ObjectError err) {
        var bean = hackBean(err);
        return (bean == null) ? null : bean.getClass();
    }

    /**
     * Retrieves the JSON field name for the given FieldError.
     *
     * @param err the FieldError to inspect
     * @return the JSON field name or the original field name if not found
     */
    public static String hackJsonFieldName(FieldError err) {
        var fieldName = err.getField();

        var klass = hackBeanClass(err);
        if (klass == null) {
            return fieldName;
        }

        return hackJsonFieldName(klass, fieldName);
    }

    private static final Map<String, String> FIELD_NAMES_CACHE = new ConcurrentHashMap<>();

    /**
     * Retrieves the JSON field name for the given class and field name.
     *
     * @param klass the class to inspect
     * @param fieldName the field name to inspect
     * @return the JSON field name or the original field name if not found
     */
    public static String hackJsonFieldName(Class<?> klass, String fieldName) {
        return FIELD_NAMES_CACHE.computeIfAbsent(
                klass.getName() + ":" + fieldName,
                _k -> {
                    Field f;
                    try {
                        f = klass.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        throw new IllegalStateException(e);
                    }
                    var jp = f.getAnnotation(JsonProperty.class);
                    if (jp == null) {
                        return fieldName;
                    }
                    if (jp.value().isEmpty()) {
                        return fieldName;
                    }
                    return jp.value();
                });
    }
}

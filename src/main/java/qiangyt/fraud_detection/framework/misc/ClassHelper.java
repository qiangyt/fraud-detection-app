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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import qiangyt.fraud_detection.framework.errs.BadRequest;
import qiangyt.fraud_detection.framework.errs.ErrorCode;
import qiangyt.fraud_detection.framework.errs.Internal;

/** */
@Slf4j
public class ClassHelper {

    private static final DefaultConversionService CONVERTERS = new DefaultConversionService();

    static {
        registerNum2DateConverter(CONVERTERS);
    }

    private ClassHelper() {
        // do nothing
    }

    public static void registerNum2DateConverter(DefaultConversionService converters) {
        Converter<Number, Date> num2DateConverter =
                new Converter<Number, Date>() {
                    @Override
                    public Date convert(Number source) {
                        return new Date(source.longValue());
                    }
                };

        converters.addConverter(byte.class, Date.class, num2DateConverter);
        converters.addConverter(Byte.class, Date.class, num2DateConverter);
        converters.addConverter(short.class, Date.class, num2DateConverter);
        converters.addConverter(Short.class, Date.class, num2DateConverter);
        converters.addConverter(int.class, Date.class, num2DateConverter);
        converters.addConverter(Integer.class, Date.class, num2DateConverter);
        converters.addConverter(long.class, Date.class, num2DateConverter);
        converters.addConverter(Long.class, Date.class, num2DateConverter);
        converters.addConverter(float.class, Date.class, num2DateConverter);
        converters.addConverter(Float.class, Date.class, num2DateConverter);
        converters.addConverter(double.class, Date.class, num2DateConverter);
        converters.addConverter(Double.class, Date.class, num2DateConverter);
        converters.addConverter(BigInteger.class, Date.class, num2DateConverter);
        converters.addConverter(BigDecimal.class, Date.class, num2DateConverter);
    }

    /**
     * @param clazz
     * @return
     */
    public static String parseNameSuffix(Class<?> clazz) {
        String n = clazz.getSimpleName();
        int pos = n.lastIndexOf('.');
        if (pos < 0) {
            return n;
        }
        return n.substring(pos + 1);
    }

    public static final Class<?>[] EMPTY_TYPES = new Class<?>[0];

    public static final Object[] EMPTY_OBJECTS = new Object[0];

    /**
     * @param beanClazz
     * @param propertyName
     * @param propertyClazz
     * @return
     */
    public static Method resolveGetter(
            Class<?> beanClazz, String propertyName, Class<?> propertyClazz) {
        Method r = resolveGetter(beanClazz, propertyName);
        if (r != null && !propertyClazz.equals(r.getReturnType())) {
            throw new Internal(
                    "Unexpected return type of getter method for property %s in class %s. We expected %s, but got %s",
                    propertyName,
                    beanClazz.getName(),
                    propertyClazz.getName(),
                    r.getReturnType().getName());
        }
        return r;
    }

    /**
     * @param beanClazz
     * @param propertyName
     * @return
     */
    public static Method resolveGetter(Class<?> beanClazz, String propertyName) {
        String capitalizedPropertyName =
                propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        String getterName = "get" + capitalizedPropertyName;
        Method r;
        try {
            r = beanClazz.getMethod(getterName, EMPTY_TYPES);
        } catch (NoSuchMethodException ex) {
            String iserName = "is" + capitalizedPropertyName;
            try {
                r = beanClazz.getMethod(iserName, EMPTY_TYPES);
            } catch (NoSuchMethodException ex1) {
                return null;
            }
        }

        return r;
    }

    /**
     * @param beanClazz
     * @param propertyName
     * @param propertyClazz
     * @return
     */
    public static Method resolveSetter(
            Class<?> beanClazz, String propertyName, Class<?> propertyClazz) {
        String capitalizedPropertyName =
                propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        String setterName = "set" + capitalizedPropertyName;
        Method r;
        try {
            r = beanClazz.getMethod(setterName, propertyClazz);
        } catch (NoSuchMethodException ex) {
            return null;
        }

        return r;
    }

    /**
     * @param clazz
     * @param pkName
     * @return
     */
    public static <S> Pair<Method, Method> resolveAccessor(Class<S> clazz, String propertyName) {
        Method getter = resolveGetter(clazz, propertyName);
        if (getter == null) {
            throw new Internal("failed to find getter for property: " + propertyName);
        }

        Method setter = resolveSetter(clazz, propertyName, getter.getReturnType());
        if (setter == null) {
            throw new Internal("failed to find getter for property: " + propertyName);
        }

        return Pair.of(getter, setter);
    }

    /**
     * @param map
     * @param bean
     * @param propertyName
     * @param getter
     */
    public static void populatePropertyIntoMap(
            Map<String, Object> map, Object bean, String propertyName, Method getter) {
        Object propertyValue = invokeGetter(bean, getter);
        map.put(propertyName, propertyValue);
    }

    /**
     * @param bean
     * @param getter
     * @return
     */
    public static Object invokeGetter(Object bean, Method getter) {
        try {
            return getter.invoke(bean, EMPTY_OBJECTS);
        } catch (ReflectiveOperationException ex) {
            throw new Internal(
                    ExceptionHelper.getRootCause(ex), "failed to invoke %s", getter.getName());
        }
    }

    /**
     * @param bean
     * @param setter
     * @param value
     * @return
     */
    public static Object invokeSetter(Object bean, Method setter, Object value) {
        try {
            return setter.invoke(bean, value);
        } catch (ReflectiveOperationException ex) {
            throw new Internal(
                    ExceptionHelper.getRootCause(ex), "failed to invoke %s", setter.getName());
        }
    }

    public static Object normalizeValue(String name, Object value, Class<?> clazz) {
        TypeDescriptor srcType = TypeDescriptor.forObject(value);
        TypeDescriptor targetType = TypeDescriptor.valueOf(clazz);
        try {
            return CONVERTERS.convert(value, srcType, targetType);
        } catch (ConversionException e) {
            throw new BadRequest(
                    ErrorCode.WRONG_DATA_FORMAT, "%s must be a %s", name, clazz.getName());
        }
    }
}

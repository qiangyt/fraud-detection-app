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
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import qiangyt.fraud_detection.framework.errs.Internal;

public class ClassHelperTest {

    public static class TargetBean {

        private String name;

        private boolean enabled;

        public static final Method GETTER_fail;

        public static final Method GETTER_name, SETTER_name;

        public static final Method GETTER_enabled;

        static {
            GETTER_fail = findMethod("getFail");

            GETTER_name = findMethod("getName");
            SETTER_name = findMethod("setName", String.class);

            GETTER_enabled = findMethod("isEnabled");
        }

        public static Method findMethod(String name, Class<?>... parameterTypes) {
            try {
                return TargetBean.class.getDeclaredMethod(name, parameterTypes);
            } catch (NoSuchMethodException ex) {
                throw new Internal(ex);
            }
        }

        public String getFail() {
            throw new IllegalArgumentException("test");
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return this.enabled;
        }
    }

    @Test
    public void test_resolveSetter_happy() {
        Method actual = ClassHelper.resolveSetter(TargetBean.class, "name", String.class);
        Assertions.assertEquals(TargetBean.SETTER_name, actual);
    }

    @Test
    public void test_resolveSetter_methodNameNotMatches() {
        Method actual = ClassHelper.resolveSetter(TargetBean.class, "wrongName", String.class);
        Assertions.assertNull(actual);
    }

    @Test
    public void test_resolveSetter_parameterTypeNotMatches() {
        Method actual = ClassHelper.resolveSetter(TargetBean.class, "name", Integer.class);
        Assertions.assertNull(actual);
    }

    @Test
    public void test_resolveGetter_happy() {
        Method actual = ClassHelper.resolveGetter(TargetBean.class, "name", String.class);
        Assertions.assertEquals(TargetBean.GETTER_name, actual);
    }

    @Test
    public void test_resolveGetter_iser() {
        Method actual = ClassHelper.resolveGetter(TargetBean.class, "enabled", boolean.class);
        Assertions.assertEquals(TargetBean.GETTER_enabled, actual);
    }

    @Test
    public void test_resolveGetter_methodNameNotMatches() {
        Method actual = ClassHelper.resolveGetter(TargetBean.class, "wrongName", String.class);
        Assertions.assertNull(actual);
    }

    @Test
    public void test_resolveGetter_returnTypeNotMatches() {
        Assertions.assertThrows(
                Internal.class,
                () -> {
                    ClassHelper.resolveGetter(TargetBean.class, "name", Integer.class);
                });
    }

    @Test
    public void test_populatePropertiesIntoMap_happy() {
        Map<String, Object> map = new HashMap<>();

        TargetBean bean = new TargetBean();
        bean.setName("test_populatePropertiesIntoMap_happy");

        ClassHelper.populatePropertyIntoMap(map, bean, "name", TargetBean.GETTER_name);

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals("test_populatePropertiesIntoMap_happy", map.get("name"));
    }

    @Test
    public void test_populatePropertiesIntoMap_fail() {
        Assertions.assertThrows(
                Internal.class,
                () -> {
                    ClassHelper.populatePropertyIntoMap(
                            new HashMap<String, Object>(),
                            new TargetBean(),
                            "fail",
                            TargetBean.GETTER_fail);
                });
    }
}

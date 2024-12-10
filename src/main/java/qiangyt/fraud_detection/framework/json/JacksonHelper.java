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
package qiangyt.fraud_detection.framework.json;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Nonnull;
import java.io.InputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Helper class for Jackson JSON operations. */
@Configuration
public class JacksonHelper {

    @Nonnull public static final Jackson DEFAULT = Jackson.DEFAULT;

    /**
     * Provides a default Jackson bean if none is already configured.
     *
     * @return the default Jackson instance
     */
    @Bean
    @ConditionalOnMissingBean
    public Jackson jackson() {
        return Jackson.DEFAULT;
    }

    /**
     * Converts an object to a pretty-printed JSON string.
     *
     * @param object the object to convert
     * @return the pretty-printed JSON string
     */
    public static String pretty(Object object) {
        return DEFAULT.pretty(object);
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object the object to convert
     * @return the JSON string
     */
    public static String to(Object object) {
        return DEFAULT.str(object, false);
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param json the JSON string
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the object
     */
    public static <T> T from(String json, @Nonnull Class<T> clazz) {
        return DEFAULT.from(json, clazz);
    }

    /**
     * Converts a JSON string to an object of the specified type reference.
     *
     * @param json the JSON string
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the object
     */
    public static <T> T from(String json, @Nonnull TypeReference<T> typeReference) {
        return DEFAULT.from(json, typeReference);
    }

    /**
     * Converts a JSON input stream to an object of the specified class.
     *
     * @param json the JSON input stream
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the object
     */
    public static <T> T from(InputStream json, @Nonnull Class<T> clazz) {
        return DEFAULT.from(json, clazz);
    }

    /**
     * Converts a JSON input stream to an object of the specified type reference.
     *
     * @param json the JSON input stream
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the object
     */
    public static <T> T from(InputStream json, @Nonnull TypeReference<T> typeReference) {
        return DEFAULT.from(json, typeReference);
    }
}

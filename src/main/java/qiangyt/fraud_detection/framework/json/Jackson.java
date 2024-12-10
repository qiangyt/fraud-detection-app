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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import qiangyt.fraud_detection.framework.misc.StringHelper;

/**
 * Jackson utility class for JSON serialization and deserialization. Provides methods to configure
 * and use Jackson ObjectMapper.
 */
@Getter
public class Jackson {

    /** Default Jackson instance with standard configuration. */
    @Nonnull public static final Jackson DEFAULT = new Jackson(buildDefaultMapper(false));

    /** Jackson instance with pretty print enabled. */
    @Nonnull public static final Jackson DUMP = new Jackson(buildDefaultMapper(true));

    public final ObjectMapper mapper;

    /**
     * Constructor to initialize Jackson with a given ObjectMapper.
     *
     * @param mapper the ObjectMapper to use
     */
    public Jackson(@Nonnull ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Builds a default ObjectMapper with standard or pretty print configuration.
     *
     * @param dump whether to enable pretty print
     * @return the configured ObjectMapper
     */
    @Nonnull
    public static ObjectMapper buildDefaultMapper(boolean dump) {
        var r = new ObjectMapper();
        initDefaultMapper(r, dump);
        return r;
    }

    /**
     * Initializes the given ObjectMapper with standard or pretty print configuration.
     *
     * @param mapper the ObjectMapper to configure
     * @param dump whether to enable pretty print
     */
    public static void initDefaultMapper(@Nonnull ObjectMapper mapper, boolean dump) {
        mapper.setSerializationInclusion(Include.ALWAYS);

        mapper.registerModule(new JavaTimeModule());

        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, false);
        mapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, false);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
        mapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, false);
        mapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, false);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
        mapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        mapper.configure(
                DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, false);
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true);
        mapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, true);
        mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, false);
        mapper.configure(SerializationFeature.CLOSE_CLOSEABLE, false);
        mapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false);
        mapper.configure(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, true);
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, true);
        mapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, false);
        mapper.configure(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX, false);
        // mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        // mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        // mapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);
        mapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, false);
    }

    /**
     * Registers a module with the ObjectMapper.
     *
     * @param module the module to register
     */
    public void registerModule(@Nonnull com.fasterxml.jackson.databind.Module module) {
        getMapper().registerModule(module);
    }

    /**
     * Replaces the existing Jackson converter in the list of converters.
     *
     * @param converters the list of converters
     */
    public void replaceConverter(List<HttpMessageConverter<?>> converters) {
        int position = 0;
        for (int i = converters.size() - 1; i >= 0; i--) {
            var converter = converters.get(i);
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                position = i;
                converters.remove(i);
            }
        }

        var jacksonConverter = new MappingJackson2HttpMessageConverter(getMapper());

        converters.add(position, jacksonConverter);
    }

    /**
     * Deserializes JSON string to an object of the specified class.
     *
     * @param text the JSON string
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(String text, @Nonnull Class<T> clazz) {
        if (StringHelper.isBlank(text)) {
            return null;
        }

        try {
            return getMapper().readValue(text, clazz);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON node to an object of the specified class.
     *
     * @param node the JSON node
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(JsonNode node, @Nonnull Class<T> clazz) {
        if (node == null) {
            return null;
        }

        try {
            return getMapper().treeToValue(node, clazz);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON from an InputStream to an object of the specified class.
     *
     * @param stream the InputStream
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(InputStream stream, @Nonnull Class<T> clazz) {
        if (stream == null) {
            return null;
        }

        try {
            return getMapper().readValue(stream, clazz);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON from a ByteBuffer to an object of the specified class.
     *
     * @param buf the ByteBuffer
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(ByteBuffer buf, @Nonnull Class<T> clazz) {
        if (buf == null) {
            return null;
        }

        try {
            if (!buf.hasArray()) {
                var bytes = new byte[buf.remaining()];
                buf.get(bytes);
                return getMapper().readValue(bytes, clazz);
            }

            final var offset = buf.arrayOffset();
            return getMapper()
                    .readValue(buf.array(), offset + buf.position(), buf.remaining(), clazz);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON byte array to an object of the specified class.
     *
     * @param bytes the byte array
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(byte[] bytes, @Nonnull Class<T> clazz) {
        if (bytes == null) {
            return null;
        }

        try {
            return getMapper().readValue(bytes, clazz);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON string to an object of the specified type reference.
     *
     * @param text the JSON string
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(String text, @Nonnull TypeReference<T> typeReference) {
        if (StringHelper.isBlank(text)) {
            return null;
        }

        try {
            return getMapper().readValue(text, typeReference);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON node to an object of the specified type reference.
     *
     * @param node the JSON node
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(JsonNode node, @Nonnull TypeReference<T> typeReference) {
        if (node == null) {
            return null;
        }

        try {
            return getMapper().treeToValue(node, typeReference);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON from an InputStream to an object of the specified type reference.
     *
     * @param stream the InputStream
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(InputStream stream, @Nonnull TypeReference<T> typeReference) {
        if (stream == null) {
            return null;
        }

        try {
            return getMapper().readValue(stream, typeReference);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON from a ByteBuffer to an object of the specified type reference.
     *
     * @param buf the ByteBuffer
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(ByteBuffer buf, @Nonnull TypeReference<T> typeReference) {
        if (buf == null) {
            return null;
        }

        try {
            if (!buf.hasArray()) {
                var bytes = new byte[buf.remaining()];
                buf.get(bytes);
                return getMapper().readValue(bytes, typeReference);
            }

            var offset = buf.arrayOffset();
            return getMapper()
                    .readValue(
                            buf.array(), offset + buf.position(), buf.remaining(), typeReference);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Deserializes JSON byte array to an object of the specified type reference.
     *
     * @param bytes the byte array
     * @param typeReference the type reference of the object
     * @param <T> the type of the object
     * @return the deserialized object
     */
    public <T> T from(byte[] bytes, @Nonnull TypeReference<T> typeReference) {
        if (bytes == null) {
            return null;
        }

        try {
            return getMapper().readValue(bytes, typeReference);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Serializes an object to a pretty JSON string.
     *
     * @param obj the object to serialize
     * @return the JSON string
     */
    public String pretty(Object obj) {
        return str(obj, true);
    }

    /**
     * Serializes an object to a JSON string.
     *
     * @param obj the object to serialize
     * @return the JSON string
     */
    public String str(Object obj) {
        return str(obj, false);
    }

    /**
     * Serializes an object to a JSON byte array.
     *
     * @param obj the object to serialize
     * @return the JSON byte array
     */
    public byte[] bytes(Object obj) {
        return bytes(obj, false);
    }

    /**
     * Serializes an object to a JSON ByteBuffer.
     *
     * @param obj the object to serialize
     * @return the JSON ByteBuffer
     */
    public ByteBuffer byteBuffer(Object obj) {
        return byteBuffer(obj, false);
    }

    /**
     * Serializes an object to a JSON string with optional pretty print.
     *
     * @param obj the object to serialize
     * @param pretty whether to enable pretty print
     * @return the JSON string
     */
    public String str(Object obj, boolean pretty) {
        if (obj == null) {
            return null;
        }

        try {
            if (pretty) {
                return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
            return getMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Serializes an object to a JSON byte array with optional pretty print.
     *
     * @param obj the object to serialize
     * @param pretty whether to enable pretty print
     * @return the JSON byte array
     */
    public byte[] bytes(Object obj, boolean pretty) {
        if (obj == null) {
            return null;
        }
        return byteBuffer(obj, pretty).array();
    }

    /**
     * Serializes an object to a JSON ByteBuffer with optional pretty print.
     *
     * @param obj the object to serialize
     * @param pretty whether to enable pretty print
     * @return the JSON ByteBuffer
     */
    public ByteBuffer byteBuffer(Object obj, boolean pretty) {
        if (obj == null) {
            return null;
        }

        try {
            var buf = new ByteArrayBuilder();

            if (pretty) {
                getMapper().writerWithDefaultPrettyPrinter().writeValue(buf, obj);
            } else {
                getMapper().writeValue(buf, obj);
            }

            return ByteBuffer.wrap(buf.getCurrentSegment(), 0, buf.getCurrentSegmentLength());
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }
}

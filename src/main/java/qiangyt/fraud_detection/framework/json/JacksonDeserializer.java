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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.annotation.Nonnull;
import java.io.IOException;

public abstract class JacksonDeserializer<T> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException, JsonProcessingException {
        var text = p.getValueAsString();
        if (text == null) {
            return null;
        }

        try {
            return deserialize(text);
        } catch (RuntimeException | IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalError(ex);
        }
    }

    @Nonnull
    public T deserialize(@Nonnull String text) throws Exception {
        throw new InternalError("deserialization is NOT supported");
    }
}

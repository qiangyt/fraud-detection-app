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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Nonnull;
import java.io.IOException;
import lombok.Getter;

@Getter
public abstract class JacksonSerializer<T> extends JsonSerializer<T> {

    final boolean dump;

    protected JacksonSerializer(boolean dump) {
        this.dump = dump;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {

        if (value == null) {
            gen.writeNull();
        } else {
            try {
                if (isDump()) {
                    dump(value, gen);
                } else {
                    serialize(value, gen);
                }
            } catch (RuntimeException | IOException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new InternalError(ex);
            }
        }
    }

    protected void dump(@Nonnull T value, @Nonnull JsonGenerator gen) throws Exception {
        throw new InternalError("serialization is NOT supported");
    }

    protected void serialize(@Nonnull T value, @Nonnull JsonGenerator gen) throws Exception {
        dump(value, gen);
    }
}

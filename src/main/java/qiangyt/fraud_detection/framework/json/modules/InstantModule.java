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
package qiangyt.fraud_detection.framework.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import qiangyt.fraud_detection.framework.json.JacksonDeserializer;
import qiangyt.fraud_detection.framework.json.JacksonSerializer;

public class InstantModule {

    @Nonnull
    public static SimpleModule build(boolean dump) {
        var r = new SimpleModule();
        r.addSerializer(Instant.class, new Serializer(dump));
        r.addDeserializer(Instant.class, new Deserializer());
        return r;
    }

    public static class Serializer extends JacksonSerializer<Instant> {

        public Serializer(boolean dump) {
            super(dump);
        }

        @Override
        protected void dump(Instant value, @Nonnull JsonGenerator gen) throws Exception {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JacksonDeserializer<Instant> {

        @Override
        public Instant deserialize(@Nonnull String text) throws Exception {
            return Instant.parse(text);
        }
    }
}

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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {

    /** ISO-8601 Date format */
    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /** */
    public static final DateTimeFormatter ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /** */
    public static final String DEFAULT_FORMAT = ISO_FORMAT;

    /** */
    public static final DateTimeFormatter DEFAULT = ISO;

    public static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

    public static final ZoneOffset SYSTEM_OFFSET = SYSTEM_ZONE.getRules().getOffset(Instant.now());

    public static OffsetDateTime offsetDateTime(Instant instant) {
        return instant.atOffset(SYSTEM_OFFSET);
    }

    public static OffsetDateTime offsetDateTime(Date date) {
        return offsetDateTime(date.toInstant());
    }
}

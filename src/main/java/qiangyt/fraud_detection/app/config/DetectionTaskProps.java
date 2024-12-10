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
package qiangyt.fraud_detection.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Configuration properties for the detection task. */
@lombok.Getter
@lombok.Setter
@ConfigurationProperties(prefix = "app.detection-task")
public class DetectionTaskProps {

    /** The capacity of the queue used for detection tasks. */
    int queueCapacity = 500;

    /** The number of seconds to wait for termination. */
    int AwaitTerminationSeconds = 60;
}

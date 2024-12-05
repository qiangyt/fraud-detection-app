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
package qiangyt.fraud_detection.sdk.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import qiangyt.fraud_detection.framework.json.JacksonHelper;
import qiangyt.fraud_detection.framework.misc.UpdateReq;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.experimental.SuperBuilder
public class TenantUpdateReq implements UpdateReq {

    @Size(max = 50)
    String code;

    @NotBlank
    @Size(max = 100)
    String name;

    @NotNull int version;

    @Override
    public String toString() {
        return JacksonHelper.pretty(this);
    }
}

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
package qiangyt.fraud_detection.sdk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import qiangyt.fraud_detection.framework.misc.UuidHelper;

@lombok.Getter
@lombok.Setter
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
@lombok.experimental.Accessors(chain = true)
public class DetectionReq {

    @NotBlank private String accountId;

    @NotNull private int amount;

    private String memo;

    public DetectionReqEntity toEntity() {
        return DetectionReqEntity.builder()
                .accountId(getAccountId())
                .amount(getAmount())
                .memo(getMemo())
                .id(UuidHelper.shortUuid())
                .receivedAt(new Date())
                .build();
    }
}

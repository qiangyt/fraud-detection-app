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
package qiangyt.fraud_detection.app.alert;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.sdk.DetectionResult;

@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
@Service
@Primary
public class GroupedAlerter implements Alerter {

    final List<Alerter> alerters = new ArrayList<>();

    void registerAlerter(Alerter alerter) {
        getAlerters().add(alerter);
    }

    @Override
    public void send(DetectionResult alert) {
        for (Alerter alerter : alerters) {
            try {
                alerter.send(alert);
            } catch (Exception ex) {
                log.error("failed to send alert", ex);
            }
        }
    }
}

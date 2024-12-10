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

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.queue.SqsBaseQueue;
import qiangyt.fraud_detection.sdk.DetectionResult;

/**
 * SqsAlerter is responsible for sending alert messages to an SQS queue. It extends SqsBaseQueue and
 * implements the Alerter interface.
 */
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
@Service
public class SqsAlerter extends SqsBaseQueue<DetectionResult> implements Alerter {

    @Autowired GroupedAlerter group;

    /** Initializes the SqsAlerter by registering it with the GroupedAlerter. */
    @PostConstruct
    void init() {
        getGroup().registerAlerter(this);
    }

    /**
     * Sends a DetectionResult to the configured SQS queue.
     *
     * @param result the DetectionResult to be sent
     */
    @Override
    public void send(DetectionResult result) {
        send(getProps().getAlertQueueUrl(), result, result.getEntity().getId(), "alert");
    }
}

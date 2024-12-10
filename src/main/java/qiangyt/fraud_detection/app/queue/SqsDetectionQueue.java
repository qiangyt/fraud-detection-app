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
package qiangyt.fraud_detection.app.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiangyt.fraud_detection.app.config.SqsProps;
import qiangyt.fraud_detection.framework.json.Jackson;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import software.amazon.awssdk.services.sqs.SqsClient;

/** Service for handling SQS detection queue operations. */
@lombok.Getter
@lombok.Setter
@lombok.extern.slf4j.Slf4j
@Service
public class SqsDetectionQueue extends SqsBaseQueue<DetectionReqEntity>
        implements DetectionRequestQueue {

    @Autowired SqsProps props;

    @Autowired SqsClient client;

    @Autowired Jackson jackson;

    /**
     * Sends a detection request entity to the SQS detection queue.
     *
     * @param req the detection request entity to be sent
     */
    @Override
    public void send(DetectionReqEntity req) {
        send(getProps().getDetectQueueUrl(), req, "", "");
    }
}

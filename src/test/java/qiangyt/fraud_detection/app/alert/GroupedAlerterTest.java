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

import static org.mockito.Mockito.*;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import qiangyt.fraud_detection.sdk.DetectionReqEntity;
import qiangyt.fraud_detection.sdk.DetectionResult;
import qiangyt.fraud_detection.sdk.FraudCategory;

public class GroupedAlerterTest {

    @Mock private Alerter alerter1;

    @Mock private Alerter alerter2;

    @InjectMocks private GroupedAlerter groupedAlerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        groupedAlerter.registerAlerter(alerter1);
        groupedAlerter.registerAlerter(alerter2);
    }

    @Test
    public void testSend() {
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        groupedAlerter.send(result);

        verify(alerter1).send(result);
        verify(alerter2).send(result);
    }

    @Test
    public void testRegisterAlerter() {
        var newAlerter = mock(Alerter.class);
        groupedAlerter.registerAlerter(newAlerter);

        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        groupedAlerter.send(result);

        verify(newAlerter).send(result);
    }

    @Test
    public void testSendWithException() {
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        doThrow(new RuntimeException("Test exception")).when(alerter1).send(result);

        groupedAlerter.send(result);

        verify(alerter1).send(result);
        verify(alerter2).send(result);
    }
}

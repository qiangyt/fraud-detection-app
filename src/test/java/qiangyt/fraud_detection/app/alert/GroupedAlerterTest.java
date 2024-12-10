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

/** Unit tests for the GroupedAlerter class. */
public class GroupedAlerterTest {

    @Mock private Alerter alerter1;

    @Mock private Alerter alerter2;

    @InjectMocks private GroupedAlerter groupedAlerter;

    /** Sets up the test environment by initializing mocks and registering alerters. */
    @BeforeEach
    public void setUp() {
        // Initialize mocks and register alerters
        MockitoAnnotations.openMocks(this);
        groupedAlerter.registerAlerter(alerter1);
        groupedAlerter.registerAlerter(alerter2);
    }

    /** Tests sending a detection result to all registered alerters. */
    @Test
    public void testSend() {
        // Create a detection request entity and result
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        // Send the result using the grouped alerter
        groupedAlerter.send(result);

        // Verify that both alerters received the send call
        verify(alerter1).send(result);
        verify(alerter2).send(result);
    }

    /** Tests registering a new alerter and sending a detection result. */
    @Test
    public void testRegisterAlerter() {
        // Create and register a new alerter
        var newAlerter = mock(Alerter.class);
        groupedAlerter.registerAlerter(newAlerter);

        // Create a detection request entity and result
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        // Send the result using the grouped alerter
        groupedAlerter.send(result);

        // Verify that the new alerter received the send call
        verify(newAlerter).send(result);
    }

    /** Tests sending a detection result when one alerter throws an exception. */
    @Test
    public void testSendWithException() {
        // Create a detection request entity and result
        var entity =
                DetectionReqEntity.builder()
                        .accountId("account-id")
                        .amount(123)
                        .memo("memo")
                        .id("entity-id")
                        .receivedAt(new Date())
                        .build();
        var result = DetectionResult.from(entity, FraudCategory.SUSPICIOUS_ACCOUNT);

        // Simulate an exception being thrown by alerter1
        doThrow(new RuntimeException("Test exception")).when(alerter1).send(result);

        // Send the result using the grouped alerter
        groupedAlerter.send(result);

        // Verify that both alerters received the send call despite the exception
        verify(alerter1).send(result);
        verify(alerter2).send(result);
    }
}

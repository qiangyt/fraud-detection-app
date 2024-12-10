
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

    @Mock
    private Alerter alerter1;

    @Mock
    private Alerter alerter2;

    @InjectMocks
    private GroupedAlerter groupedAlerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        groupedAlerter.registerAlerter(alerter1);
        groupedAlerter.registerAlerter(alerter2);
    }

    @Test
    public void testSend() {
        var entity = DetectionReqEntity.builder()
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

        var entity = DetectionReqEntity.builder()
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
}
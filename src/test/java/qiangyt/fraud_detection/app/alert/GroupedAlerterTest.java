package qiangyt.fraud_detection.app.alert;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GroupedAlerterTest {

    @Mock
    private Alerter alerter1;

    @Mock
    private Alerter alerter2;

    private GroupedAlerter groupedAlerter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        groupedAlerter = new GroupedAlerter();
        groupedAlerter.registerAlerter(alerter1);
        groupedAlerter.registerAlerter(alerter2);
    }

    @Test
    public void testSend_AlertSentSuccessfully() {
        // Arrange
        DetectionResult alert = new DetectionResult();

        // Act
        groupedAlerter.send(alert);

        // Assert
        verify(alerter1, times(1)).send(alert);
        verify(alerter2, times(1)).send(alert);
    }

    @Test
    public void testSend_FirstAlerterFails_SecondAlerterSendsAlert() {
        // Arrange
        DetectionResult alert = new DetectionResult();
        doThrow(new RuntimeException("Error")).when(alerter1).send(any());

        // Act
        groupedAlerter.send(alert);

        // Assert
        verify(alerter1, times(1)).send(alert);
        verify(alerter2, times(1)).send(alert);
    }

    @Test
    public void testSend_NoAlertersRegistered() {
        // Arrange
        GroupedAlerter emptyGroupedAlerter = new GroupedAlerter();
        DetectionResult alert = new DetectionResult();

        // Act
        emptyGroupedAlerter.send(alert);

        // Assert
        verify(alerter1, never()).send(any());
        verify(alerter2, never()).send(any());
    }

    @Test
    public void testSend_AlertersListIsNull() {
        // Arrange
        GroupedAlerter nullAlertersGroupedAlerter = new GroupedAlerter();
        DetectionResult alert = new DetectionResult();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> nullAlertersGroupedAlerter.send(alert));
    }

    @Test
    public void testSend_AlertersListIsEmpty() {
        // Arrange
        GroupedAlerter emptyAlertersGroupedAlerter = new GroupedAlerter();
        DetectionResult alert = new DetectionResult();

        // Act
        emptyAlertersGroupedAlerter.send(alert);

        // Assert
        verify(alerter1, never()).send(any());
        verify(alerter2, never()).send(any());
    }
}

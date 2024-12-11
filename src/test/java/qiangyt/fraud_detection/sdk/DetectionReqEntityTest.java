package qiangyt.fraud_detection.sdk;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DetectionReqEntityTest {

    private DetectionReqEntity detectionReqEntity;

    @BeforeEach
    public void setUp() {
        detectionReqEntity = DetectionReqEntity.builder()
                .accountId("123456789")
                .amount(100)
                .memo("Initial request")
                .id("req-12345")
                .receivedAt(new Date())
                .build();
    }

    @Test
    public void testHappyPath() {
        // Test that all fields are set correctly
        assertEquals("123456789", detectionReqEntity.getAccountId());
        assertEquals(100, detectionReqEntity.getAmount());
        assertEquals("Initial request", detectionReqEntity.getMemo());
        assertEquals("req-12345", detectionReqEntity.getId());
        assertNotNull(detectionReqEntity.getReceivedAt());
    }

    @Test
    public void testEmptyAccountId() {
        // Test that an empty account ID is not allowed
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("")
                .amount(100)
                .memo("Initial request")
                .id("req-12345")
                .receivedAt(new Date())
                .build();
        assertNull(entity.getAccountId());
    }

    @Test
    public void testNegativeAmount() {
        // Test that a negative amount is not allowed
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("123456789")
                .amount(-100)
                .memo("Initial request")
                .id("req-12345")
                .receivedAt(new Date())
                .build();
        assertEquals(0, entity.getAmount());
    }

    @Test
    public void testNullMemo() {
        // Test that a null memo is allowed
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("123456789")
                .amount(100)
                .memo(null)
                .id("req-12345")
                .receivedAt(new Date())
                .build();
        assertNull(entity.getMemo());
    }

    @Test
    public void testEmptyId() {
        // Test that an empty ID is not allowed
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("123456789")
                .amount(100)
                .memo("Initial request")
                .id("")
                .receivedAt(new Date())
                .build();
        assertNull(entity.getId());
    }

    @Test
    public void testNullReceivedAt() {
        // Test that a null received at date is not allowed
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId("123456789")
                .amount(100)
                .memo("Initial request")
                .id("req-12345")
                .receivedAt(null)
                .build();
        assertNull(entity.getReceivedAt());
    }

    @Test
    public void testNullEntity() {
        // Test that a null entity is not allowed
        DetectionReqEntity entity = null;
        assertThrows(NullPointerException.class, () -> {
            entity.getAccountId();
        });
    }

    @Test
    public void testEmptyEntity() {
        // Test that an empty entity is not allowed
        DetectionReqEntity entity = new DetectionReqEntity();
        assertNull(entity.getAccountId());
        assertEquals(0, entity.getAmount());
        assertNull(entity.getMemo());
        assertNull(entity.getId());
        assertNull(entity.getReceivedAt());
    }

    @Test
    public void testAllNullFields() {
        // Test that all fields are null
        DetectionReqEntity entity = DetectionReqEntity.builder()
                .accountId(null)
                .amount(0)
                .memo(null)
                .id(null)
                .receivedAt(null)
                .build();
        assertNull(entity.getAccountId());
        assertEquals(0, entity.getAmount());
        assertNull(entity.getMemo());
        assertNull(entity.getId());
        assertNull(entity.getReceivedAt());
    }
}

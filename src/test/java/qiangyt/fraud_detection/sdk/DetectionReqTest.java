package qiangyt.fraud_detection.sdk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetectionReqTest {

    @Test
    public void testToEntity_HappyPath() {
        // Arrange
        String accountId = "12345";
        int amount = 100;
        String memo = "test memo";

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act
        DetectionReqEntity entity = request.toEntity();

        // Assert
        assertEquals(accountId, entity.getAccountId());
        assertEquals(amount, entity.getAmount());
        assertEquals(memo, entity.getMemo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getReceivedAt());
    }

    @Test
    public void testToEntity_NullAccountId() {
        // Arrange
        String accountId = null;
        int amount = 100;
        String memo = "test memo";

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            request.toEntity();
        });
    }

    @Test
    public void testToEntity_NegativeAmount() {
        // Arrange
        String accountId = "12345";
        int amount = -100;
        String memo = "test memo";

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            request.toEntity();
        });
    }

    @Test
    public void testToEntity_EmptyAccountId() {
        // Arrange
        String accountId = "";
        int amount = 100;
        String memo = "test memo";

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            request.toEntity();
        });
    }

    @Test
    public void testToEntity_NullMemo() {
        // Arrange
        String accountId = "12345";
        int amount = 100;
        String memo = null;

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act
        DetectionReqEntity entity = request.toEntity();

        // Assert
        assertEquals(accountId, entity.getAccountId());
        assertEquals(amount, entity.getAmount());
        assertNull(entity.getMemo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getReceivedAt());
    }

    @Test
    public void testToEntity_EmptyMemo() {
        // Arrange
        String accountId = "12345";
        int amount = 100;
        String memo = "";

        DetectionReq request = DetectionReq.builder()
                .accountId(accountId)
                .amount(amount)
                .memo(memo)
                .build();

        // Act
        DetectionReqEntity entity = request.toEntity();

        // Assert
        assertEquals(accountId, entity.getAccountId());
        assertEquals(amount, entity.getAmount());
        assertEquals(memo, entity.getMemo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getReceivedAt());
    }

    @Test
    public void testToEntity_NullRequest() {
        // Arrange
        DetectionReq request = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            request.toEntity();
        });
    }
}

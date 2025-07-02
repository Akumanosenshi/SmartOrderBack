package ynov.smartorder.api.web.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ynov.smartorder.api.web.services.BruteForceProtectionService;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceProtectionServiceTest {

    private BruteForceProtectionService bruteForceService;

    @BeforeEach
    void setUp() {
        bruteForceService = new BruteForceProtectionService();
    }

    @Test
    void shouldNotBeBlockedInitially() {
        // given
        String email = "test@example.com";

        // when
        boolean blocked = bruteForceService.isBlocked(email);

        // then
        assertFalse(blocked);
    }

    @Test
    void shouldBlockAfterMaxFailedAttempts() {
        // given
        String email = "blocked@example.com";

        // when
        for (int i = 0; i < 5; i++) {
            bruteForceService.recordFailedAttempt(email);
        }

        // then
        assertTrue(bruteForceService.isBlocked(email));
    }

    @Test
    void shouldResetAttemptsAndUnblock() {
        // given
        String email = "reset@example.com";
        for (int i = 0; i < 5; i++) {
            bruteForceService.recordFailedAttempt(email);
        }
        assertTrue(bruteForceService.isBlocked(email));

        // when
        bruteForceService.resetAttempts(email);

        // then
        assertFalse(bruteForceService.isBlocked(email));
    }

    @Test
    void shouldAutoUnblockAfterBlockDuration() throws InterruptedException {
        // given
        String email = "auto@unblock.com";
        for (int i = 0; i < 5; i++) {
            bruteForceService.recordFailedAttempt(email);
        }
        assertTrue(bruteForceService.isBlocked(email));

        // simulate passage of time (for test purpose ONLY: force internal state)
        Thread.sleep(1000); // sleep 1s to allow time comparison logic to engage

        // when
        bruteForceService.blockCache.put(email, Instant.now().minusSeconds(901)); // simulate >15 min
        boolean unblocked = bruteForceService.isBlocked(email);

        // then
        assertFalse(unblocked);
    }
}


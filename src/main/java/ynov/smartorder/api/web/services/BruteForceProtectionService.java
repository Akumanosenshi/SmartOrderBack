package ynov.smartorder.api.web.services;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForceProtectionService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_DURATION_MINUTES = 15;

    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    final Map<String, Instant> blockCache = new ConcurrentHashMap<>();

    public boolean isBlocked(String email) {
        if (!blockCache.containsKey(email)) {
            return false;
        }
        Instant blockTime = blockCache.get(email);
        if (Instant.now().isAfter(blockTime.plus(BLOCK_DURATION_MINUTES, ChronoUnit.MINUTES))) {
            blockCache.remove(email);
            attemptsCache.remove(email);
            return false;
        }
        return true;
    }

    public void recordFailedAttempt(String email) {
        int attempts = attemptsCache.getOrDefault(email, 0) + 1;
        attemptsCache.put(email, attempts);

        if (attempts >= MAX_ATTEMPTS) {
            blockCache.put(email, Instant.now());
        }
    }

    public void resetAttempts(String email) {
        attemptsCache.remove(email);
        blockCache.remove(email);
    }
}
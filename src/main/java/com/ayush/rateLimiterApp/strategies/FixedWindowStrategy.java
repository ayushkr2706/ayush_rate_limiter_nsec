package com.ayush.rateLimiterApp.strategies;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;

public class FixedWindowStrategy implements RateLimiterStrategy {

    private Map<String, Long> timeMap = new ConcurrentHashMap<>();
    private Map<String, Integer> counterMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String userId, RateLimitConfig config) {

        int windowSize = config.getWindowSize();
        long now = System.currentTimeMillis();
        int limit = config.getLimit();

        // To Avoid null pointer exception
        timeMap.putIfAbsent(userId, now);
        counterMap.putIfAbsent(userId, 0);

        long startTime = timeMap.get(userId);

        // If session expired
        if (now - startTime >= windowSize) {
            timeMap.put(userId, now);
            counterMap.put(userId, 0);
        }

        int counter = counterMap.get(userId);

        if (counter < limit) {
            counterMap.put(userId, counter + 1);
            return true;
        }
        return false;

    }

}

package com.ayush.rateLimiterApp.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;

@Component("SlidingWindowStrategy")
public class SlidingWindowStrategy implements RateLimiterStrategy {

    Map<String, List<Long>> log = new ConcurrentHashMap<>();

    public boolean isAllowed(String userId, RateLimitConfig config) {

        int limit = config.getLimit();
        long windowSize = config.getWindowSize() * 1000L;

        long now = System.currentTimeMillis();

        log.putIfAbsent(userId, new ArrayList<>());
        List<Long> temp = log.get(userId);

        if (temp != null) {
            temp.removeIf(i -> now - i >= windowSize);
        }

        int count = temp.size();

        if (count < limit) {
            temp.add(now);
            return true;
        }
        return false;
    }

}

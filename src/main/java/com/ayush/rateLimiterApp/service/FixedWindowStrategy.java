package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowStrategy implements RateLimiterStrategy {

    private final int limit = 5;
    private final long timeWindow = 10000;

    private final Map<Integer, Long> windowStarts = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> requestCount = new ConcurrentHashMap<>();

    @Override
    public Boolean isAllowed(int userId){
        long currentTime = System.currentTimeMillis();
        windowStarts.putIfAbsent(userId, currentTime);
        requestCount.putIfAbsent(userId, 0);

        long startTime = windowStarts.get(userId);
        if(currentTime - startTime >= timeWindow){
            windowStarts.put(userId, currentTime);
            requestCount.put(userId, 0);
        }

        int count = requestCount.get(userId);
        if(count < limit){
            requestCount.put(userId, count+1);
            return true;
        }
        return false;
    }
}

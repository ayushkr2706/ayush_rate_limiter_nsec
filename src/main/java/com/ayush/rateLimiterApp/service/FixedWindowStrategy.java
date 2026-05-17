package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowStrategy implements RateLimiterStrategy {

//    private final int limit = 5;
//    private final long timeWindow = 10000;

    //windowStart will store the start time of each request.
    private final Map<Integer, Long> windowStarts = new ConcurrentHashMap<>();
    //requestCount will store the number of requests.
    private final Map<Integer, Integer> requestCount = new ConcurrentHashMap<>();

    @Override
    public Boolean isAllowed(int userId, RateLimitConfig config) {
        long currentTime = System.currentTimeMillis();

        int limit = config.getLimit();
        int timeWindow = config.getWindowSize();

        //to avoid null pointer exception
        windowStarts.putIfAbsent(userId, currentTime);
        requestCount.putIfAbsent(userId, 0);

        long startTime = windowStarts.get(userId);

        //session expired
        if(currentTime - startTime >= timeWindow){
            windowStarts.put(userId, currentTime);
            requestCount.put(userId, 0);
        }

        int count = requestCount.get(userId);

        //limit not reached
        if(count < limit){
            requestCount.put(userId, count+1);
            return true;
        }

        //if limit reached then return false;
        return false;
    }
}

package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlidingWindowStrategy implements RateLimiterStrategy {
//    private final int limit = 5;
//    private final int timeWindow = 10000; //10 seconds

    Map<Integer, List<Long>> userDb = new ConcurrentHashMap<>();

    public Boolean isAllowed(int userId, RateLimitConfig config) {
        // get current time
        //get user's previous timestamps
        //remove the timestamps that are older than 60 seconds.
        //check whether user has already made 5 requests in last 60 seconds or not.
        //if yes then don't allow otherwise allow.
        //if allowed then add that timestamps in the list and userDb.

       int limit = config.getLimit();
       int timeWindow = config.getWindowSize();
        long now = System.currentTimeMillis();
        userDb.putIfAbsent(userId, new ArrayList<>());
        List<Long> timestamps = userDb.get(userId);
        timestamps.removeIf(timestamp -> now - timestamp > timeWindow);
        if (timestamps.size() < limit){
            timestamps.add(now);
            return true;
        }
        return false;
    }
}

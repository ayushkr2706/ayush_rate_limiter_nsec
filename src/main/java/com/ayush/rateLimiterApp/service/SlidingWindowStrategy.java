package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("slidingWindowStrategy")
public class SlidingWindowStrategy implements RateLimiterStrategy{
//    private final int limit = 5;
//    private final int timeWindow = 10000; //10 seconds

    //Map<String, List<Long>> userDb = new ConcurrentHashMap<>();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean isAllowed(String userId, RateLimitConfig config) {
        // get current time
        //get user's previous timestamps
        //remove the timestamps that are older than 60 seconds.
        //check whether user has already made 5 requests in last 60 seconds or not.
        //if yes then don't allow otherwise allow.
        //if allowed then add that timestamps in the list and userDb.

       int limit = config.getLimit();
       int timeWindow = config.getWindowSize();
        long now = System.currentTimeMillis();

        String key = "sw:"+ userId;
       // userDb.putIfAbsent(userId, new ArrayList<>());
        //List<Long> timestamps = userDb.get(userId);

        redisTemplate.opsForZSet().removeRangeByScore(key, 0, now - timeWindow*1000);

        Long count = redisTemplate.opsForZSet().zCard(key);

     //   List<Long> timestamps = userDb.computeIfAbsent(userId, k -> new ArrayList<>());
       // System.out.println("Before cleanup : "+timestamps.size());
//        synchronized (timestamps) {
//            timestamps.removeIf(timestamp -> now - timestamp > timeWindow);
//            System.out.println("After cleanup : " + timestamps.size());
//            if(timestamps.size() < limit) {
//                timestamps.add(now);
//                System.out.println("After adding: " + timestamps.size());
//                return true;
//            }
//        }
//        return false;
        if (count != null && count >= limit) {
            return false;
        }

        redisTemplate.opsForZSet()
                .add(key, String.valueOf(now), now);

        redisTemplate.expire(key, Duration.ofSeconds(config.getWindowSize()));
        return true;
    }
}

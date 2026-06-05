package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TokenBucketStrategy implements RateLimiterStrategy {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean isAllowed(String userId, RateLimitConfig config){

        String key = "rate_limit:token_bucket:" + userId;
        int maxTokens = config.getLimit();
        double refillRate = (double) config.getLimit()/config.getWindowSize();
        long now = System.currentTimeMillis();

        //get data from redis
        String tokensStr = redisTemplate.opsForHash().get(key, "tokens") != null ? redisTemplate.opsForHash().get(key, "tokens").toString() : null;
        String lastRefillStr = redisTemplate.opsForHash().get(key, "lastRefill") != null ? redisTemplate.opsForHash().get(key, "lastRefill").toString() : null;

        double tokens = tokensStr != null ? Double.parseDouble(tokensStr) : maxTokens;
        long lastRefill = lastRefillStr != null ? Long.parseLong(lastRefillStr) : now;

        // refill tokens
        long timePassed = now - lastRefill;
        double newTokens = timePassed * refillRate;

        tokens = Math.min(maxTokens, tokens + newTokens);

        if (tokens < 1) {
            return false;
        }

        // consume token
        tokens -= 1;

        // save back to Redis
        redisTemplate.opsForHash().put(key, "tokens", String.valueOf(tokens));
        redisTemplate.opsForHash().put(key, "lastRefill", String.valueOf(now));

        redisTemplate.expire(key, Duration.ofMillis(config.getWindowSize()));

        return true;
    }
}

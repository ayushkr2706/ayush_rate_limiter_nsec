package com.ayush.rateLimiterApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Long increment(String key){
        return redisTemplate.opsForValue().increment(key);
    }
    public void setExpiry(String key, long seconds){
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }
    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

}

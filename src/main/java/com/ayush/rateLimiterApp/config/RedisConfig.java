package com.ayush.rateLimiterApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }
}

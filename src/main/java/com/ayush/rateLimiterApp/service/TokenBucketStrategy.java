package com.ayush.rateLimiterApp.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketStrategy {

    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> script;

    public TokenBucketStrategy(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.script = RedisScript.of(
                new ClassPathResource("scripts/tokenBucket.lua"),
                Long.class
        );      //Load your Lua script from the application's resources
                // and tell Spring what type of value the script will return.
    }

    public boolean isAllowed(String identity, int capacity, double refillRate){

        //refillRate is taken as double because suppose if the request is coming after every 0.1 sec
        //and the refillRate is 5 tokens/second, then after each 0.1 sec there should be 0.5 token
        //added to the bucket. But if we take the refillRate as an integer then it will always
        //add 0 tokens to the bucket. So after 10 seconds there should have been 50 tokens in the bucket
        //but if we take int then there will be 0 tokens.

        String key = "rateLimit:" + identity;


    }
}

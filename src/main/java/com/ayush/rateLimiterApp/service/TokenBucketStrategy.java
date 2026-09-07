package com.ayush.rateLimiterApp.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

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

        long now = Instant.now().getEpochSecond();

        /**
         * Executes the Token Bucket Lua script atomically in Redis.
         *
         * The Redis key identifies the user's token bucket, while the remaining
         * arguments provide the bucket capacity, refill rate, current timestamp,
         * and number of tokens to consume for the current request.
         *
         * return the result returned by the Lua script, indicating whether
         *         the request is allowed
         */
        Long result = redisTemplate.execute(
                script,                          //Lua script executed atomically
                Collections.singletonList(key),    //Redis key for this user's bucket
                String.valueOf(capacity),   //Maximum bucket capacity
                String.valueOf(refillRate),         //Tokens added per unit time
                String.valueOf(now),                //Current timestamp
                "1"                                 //Tokens to consume per request
        );

        return result != null && result == 1L;

    }
}

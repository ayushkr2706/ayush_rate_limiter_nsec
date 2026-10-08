package com.ayush.rateLimiterApp.rateLimiting.service;

import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimitResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TokenBucketRateLimiter {

    private final StringRedisTemplate redisTemplate;      //Through this we'll communicate with Redis.

    private final RedisScript<List> script;//It is the Lua script representation. It expects the
                                                // lua script to return a List.

    public TokenBucketRateLimiter(StringRedisTemplate redisTemplate){

        this.redisTemplate = redisTemplate;

        this.script = RedisScript.of(new ClassPathResource("scripts/tokenBucket.lua")
                                        , List.class);    //Load your Lua script from the application's resources
                                                        // and tell Spring what type of value the script will return

    }

    public  RateLimitResult isAllowed(String identity, int capacity, double refillRate){

        String redisKey = "rateLimit:" + identity;

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

        List result = redisTemplate.execute(
                script,                                 //Lua script executed atomically
                Collections.singletonList(redisKey),    //Identifies a particular user's bucket inside the redis
                String.valueOf(capacity),               //Total number of tokens the bucket can have
                String.valueOf(refillRate),             //The rate at which token will be refilled per unit time
                String.valueOf(1)                    //Number of tokens to be consumed per request
        );

        if(result == null || result.size()<3){
            throw new IllegalStateException(
                    "Unexpected response from the rate limit script");
        }

        boolean allowed = false;
        Number allowedValue = (Number) result.get(0);

        if (allowedValue.longValue() == 1L) {
            allowed = true;
        } else {
            allowed = false;
        }

        Number retryAfterValue = (Number) result.get(1);
        double retryAfter = retryAfterValue.doubleValue();

        Number remainingTokensValue = (Number) result.get(2);
        int remainingTokens = remainingTokensValue.intValue();

        RateLimitResult response = new RateLimitResult();
        response.setAllowed(allowed);
        response.setRetryAfter(retryAfter);
        response.setTokens(remainingTokens);
        return response;

    }

}

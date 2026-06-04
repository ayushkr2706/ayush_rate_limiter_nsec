package com.ayush.rateLimiterApp;

import com.ayush.rateLimiterApp.config.RateLimitConfig;

public interface RateLimiterStrategy {
    public Boolean isAllowed(String userId, RateLimitConfig config);
}

package com.ayush.rateLimiterApp;

import com.ayush.rateLimiterApp.config.RateLimitConfig;

public interface RateLimiterStrategy {
    public boolean isAllowed(String userId, RateLimitConfig config);
}

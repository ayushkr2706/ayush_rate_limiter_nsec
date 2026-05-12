package com.ayush.rateLimiterApp;

public interface RateLimiterStrategy {
    public Boolean isAllowed(int userId);
}

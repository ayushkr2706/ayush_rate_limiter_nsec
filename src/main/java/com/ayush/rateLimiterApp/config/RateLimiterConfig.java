package com.ayush.rateLimiterApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterConfig {

    @Value("${rate-limiter.capacity}")
    private int capacity;

    @Value("${rate-limiter.refill-rate}")
    private double refillRate;

    public int getCapacity() {
        return capacity;
    }

    public double getRefillRate() {
        return refillRate;
    }
}

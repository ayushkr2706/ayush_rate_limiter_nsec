package com.ayush.rateLimiterApp.config;

public class RateLimitConfig {
    private String strategyType;
    private String userType;
    private int requestLimit;
    private int windowSize;

    public RateLimitConfig(String strategyType, String userType, int requestLimit, int windowSize) {
        this.strategyType = strategyType;
        this.userType = userType;
        this.requestLimit = requestLimit;
        this.windowSize = windowSize;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public int getLimit() {
        return requestLimit;
    }

    public int getWindowSize() {
        return windowSize;
    }
}

package com.ayush.rateLimiterApp.rateLimiting.dto;

public class RateLimitResult {

    private boolean allowed;
    private double retryAfter;
    private int tokens;


    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public double getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(double retryAfter) {
        this.retryAfter = retryAfter;
    }
}

package com.ayush.rateLimiterApp.rateLimiting.dto;

import java.util.UUID;

public class RateLimiterResponseDto {

    private double retryAfter;
    private boolean allowed;
    private String message;
    private int limit;
    private int remainingTokens;
    private UUID policyId;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRemainingTokens() {
        return remainingTokens;
    }

    public void setRemainingTokens(int remainingTokens) {
        this.remainingTokens = remainingTokens;
    }

    public UUID getPolicyId() {
        return policyId;
    }

    public void setPolicyId(UUID policyId) {
        this.policyId = policyId;
    }

    public double getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(double retryAfter) {
        this.retryAfter = retryAfter;
    }

    public boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.ayush.rateLimiterApp.rateLimiting.dto;

public class RateLimiterResponseDto {

    private int retryAfter;
    private String allowed;
    private String message;

    public int getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }

    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

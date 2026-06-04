package com.ayush.rateLimiterApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rate_limit_config")
public class RateLimitConfigEntity {

    @Id
    private String userId;

    private String strategyType;
    private int requestLimit;
    private int windowSize;

    // Default constructor (REQUIRED by JPA)
    public RateLimitConfigEntity() {}

    // Custom constructor
    public RateLimitConfigEntity(String userId, String strategyType, int requestLimit, int windowSize) {
        this.userId = userId;
        this.strategyType = strategyType;
        this.requestLimit = requestLimit;
        this.windowSize = windowSize;
    }

    // getters & setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public int getLimit() {
        return requestLimit;
    }
    public void setLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }
    public int getWindowSize() {
        return windowSize;
    }
    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

}
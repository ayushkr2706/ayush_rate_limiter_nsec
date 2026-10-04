package com.ayush.rateLimiterApp.PolicyManagement.dto;

import java.util.UUID;

public class PolicyResponseDto {

    private String policyId;
    private int capacity;
    private double refillRate;

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRefillRate() {
        return refillRate;
    }

    public void setRefillRate(double refillRate) {
        this.refillRate = refillRate;
    }
}

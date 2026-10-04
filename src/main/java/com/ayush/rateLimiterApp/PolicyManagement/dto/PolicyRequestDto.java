package com.ayush.rateLimiterApp.PolicyManagement.dto;

public class PolicyRequestDto {

    private int capacity;
    private double refillRate;

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

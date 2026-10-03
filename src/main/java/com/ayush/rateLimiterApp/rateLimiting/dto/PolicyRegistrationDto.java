package com.ayush.rateLimiterApp.rateLimiting.dto;

public class PolicyRegistrationDto {

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

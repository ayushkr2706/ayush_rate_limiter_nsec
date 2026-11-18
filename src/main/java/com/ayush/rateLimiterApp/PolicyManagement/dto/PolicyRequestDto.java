package com.ayush.rateLimiterApp.PolicyManagement.dto;

import com.ayush.rateLimiterApp.PolicyManagement.enums.PolicyStatus;
import jakarta.validation.constraints.NotBlank;

public class PolicyRequestDto {

    @NotBlank(message = "Capacity is required")
    private int capacity;

    @NotBlank(message = "Refill rate is required")
    private double refillRate;

    private PolicyStatus status;

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

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }
}

package com.ayush.rateLimiterApp.PolicyManagement.dto;

import com.ayush.rateLimiterApp.PolicyManagement.enums.PolicyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PolicyRequestDto {

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;

    @NotNull(message = "Refill rate is required")
    @Positive(message = "Refill rate must be greater than 0")
    private Double refillRate;

    @NotBlank(message = "Policy name cannot be blank")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.ayush.rateLimiterApp.rateLimiting.entity;

import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Policies {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID policyId;
    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenants tenant;
    private int capacity;
    private double refillRate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updated_At;

    public UUID getId() {
        return policyId;
    }

    public void setId(UUID policyId) {
        this.policyId = policyId;
    }

    public Tenants getTenant() {
        return tenant;
    }

    public void setTenant(Tenants tenant) {
        this.tenant = tenant;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(LocalDateTime updated_At) {
        this.updated_At = updated_At;
    }
}

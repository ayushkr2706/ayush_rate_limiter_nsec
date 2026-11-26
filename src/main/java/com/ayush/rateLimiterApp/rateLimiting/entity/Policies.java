package com.ayush.rateLimiterApp.rateLimiting.entity;

import com.ayush.rateLimiterApp.PolicyManagement.enums.PolicyStatus;
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
    @JoinColumn(name = "tenantId", nullable = false)
    private Tenants tenant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private double refillRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

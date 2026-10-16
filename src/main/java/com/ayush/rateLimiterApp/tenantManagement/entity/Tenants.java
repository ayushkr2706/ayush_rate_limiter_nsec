package com.ayush.rateLimiterApp.tenantManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Tenants {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID tenantId;
    private UUID parentTenantId;
    private String companyName;
    private LocalDateTime createdAt;
    private String status;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getParentTenantId() {
        return parentTenantId;
    }

    public void setParentTenantId(UUID parentTenantId) {
        this.parentTenantId = parentTenantId;
    }
}

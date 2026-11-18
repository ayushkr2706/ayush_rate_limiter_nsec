package com.ayush.rateLimiterApp.tenantManagement.entity;

import com.ayush.rateLimiterApp.tenantManagement.enums.TenantStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Tenants {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID tenantId;
    private UUID parentTenantId;

    @Column(nullable = false)
    private String companyName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status;

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

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }

    public UUID getParentTenantId() {
        return parentTenantId;
    }

    public void setParentTenantId(UUID parentTenantId) {
        this.parentTenantId = parentTenantId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

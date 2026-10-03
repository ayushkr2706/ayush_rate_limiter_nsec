package com.ayush.rateLimiterApp.apiCredentialManagement.entity;

import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ApiCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID apiKey;
    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenants tenant;
    private LocalDateTime createdAt;

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public Tenants getTenant() {
        return tenant;
    }

    public void setTenant(Tenants tenant) {
        this.tenant = tenant;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

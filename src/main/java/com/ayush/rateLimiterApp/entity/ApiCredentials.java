package com.ayush.rateLimiterApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ApiCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String apiKey;
    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenants tenant;
    private LocalDateTime createdAt;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
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

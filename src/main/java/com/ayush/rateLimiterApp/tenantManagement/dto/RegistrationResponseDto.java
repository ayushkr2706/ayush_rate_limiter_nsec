package com.ayush.rateLimiterApp.tenantManagement.dto;

import com.ayush.rateLimiterApp.tenantManagement.enums.TenantStatus;

import java.time.LocalDateTime;

public class RegistrationResponseDto {

    private String apiKey;
    private TenantStatus status;
    private LocalDateTime createdAt;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

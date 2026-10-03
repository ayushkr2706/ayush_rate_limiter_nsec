package com.ayush.rateLimiterApp.tenantManagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationResponseDto {

    private UUID apiKey;
    private String status;
    private LocalDateTime createdAt;

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
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
}

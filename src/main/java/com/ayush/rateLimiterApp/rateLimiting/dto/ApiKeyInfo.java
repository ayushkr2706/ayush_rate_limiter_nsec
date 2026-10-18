package com.ayush.rateLimiterApp.rateLimiting.dto;

import java.util.UUID;

public record ApiKeyInfo(UUID tenantId, boolean active) {
}

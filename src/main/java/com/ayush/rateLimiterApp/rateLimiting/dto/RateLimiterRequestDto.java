package com.ayush.rateLimiterApp.rateLimiting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RateLimiterRequestDto {

    @NotBlank(message = "userIp is required")
    @Size(max = 64, message = "userIp is too long")
    private String userIp;

    @NotBlank(message = "policyId is required")
    private String policyId;

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
}

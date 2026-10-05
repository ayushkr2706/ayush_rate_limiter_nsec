package com.ayush.rateLimiterApp.rateLimiting.dto;

public class RateLimiterRequestDto {

    private String userIp;
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

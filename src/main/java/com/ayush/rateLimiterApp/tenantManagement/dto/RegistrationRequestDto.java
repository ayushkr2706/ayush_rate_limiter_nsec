package com.ayush.rateLimiterApp.tenantManagement.dto;

import com.ayush.rateLimiterApp.rateLimiting.dto.PolicyRegistrationDto;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;

public class RegistrationRequestDto {

    private String companyName;
    private String companyEmail;
    private String password;
    private PolicyRegistrationDto policy;

    public PolicyRegistrationDto getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyRegistrationDto policy) {
        this.policy = policy;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

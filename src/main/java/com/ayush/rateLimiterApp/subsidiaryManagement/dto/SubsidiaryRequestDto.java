package com.ayush.rateLimiterApp.subsidiaryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubsidiaryRequestDto {

    @NotBlank(message = "company name is required")
    @Size(max = 100, message = "company name is too long")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

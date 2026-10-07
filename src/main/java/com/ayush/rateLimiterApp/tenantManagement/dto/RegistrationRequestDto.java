package com.ayush.rateLimiterApp.tenantManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequestDto {

    @NotBlank(message = "company name is required")
    @Size(max = 100, message = "company name is too long")
    private String companyName;

    @NotBlank(message = "company email is required")
    @Email(message = "company email must be a valid email")
    private String companyEmail;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 72, message = "password must be 8 to 72 characters")
    private String password;

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

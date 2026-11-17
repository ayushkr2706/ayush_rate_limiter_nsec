package com.ayush.rateLimiterApp.invitationManagement.dto;

import com.ayush.rateLimiterApp.invitationManagement.enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.UUID;

public class InvitationRequestDto {

    private String email;
    private Role role;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

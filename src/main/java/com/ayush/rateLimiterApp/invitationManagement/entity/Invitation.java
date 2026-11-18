package com.ayush.rateLimiterApp.invitationManagement.entity;

import com.ayush.rateLimiterApp.invitationManagement.enums.Role;
import com.ayush.rateLimiterApp.invitationManagement.enums.InvitationStatus;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import com.ayush.rateLimiterApp.tenantManagement.entity.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID invitationId;

    @Column(nullable = false)
    private String email;   //email of the recepient user

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    //The role in the database will be of string type.
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    //The status in the database will be of string type.
    private InvitationStatus status = InvitationStatus.PENDING;     //by default the status is PENDING.

    @ManyToOne(fetch = FetchType.LAZY)  //One invitation cannot belong to more than one Tenant.
    @JoinColumn(name = "tenant_id", nullable = false)     //and one tenant can have multiple invitations.
    private Tenants tenant;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)//One user can be present in multiple rows in invitedBy field
    @JoinColumn(name = "invited_by", nullable = false)  // means that multiple invitations can come from one user.
    private Users invitedBy;

    @Column(nullable = false)
    private String tokenHash;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime acceptedAt;

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

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public Tenants getTenant() {
        return tenant;
    }

    public void setTenant(Tenants tenant) {
        this.tenant = tenant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Users invitedBy) {
        this.invitedBy = invitedBy;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
}

package com.ayush.rateLimiterApp.tenantManagement.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "user_memberships",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "tenant_id"}
                )
        }
)
public class UserMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID membershipId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenantId", nullable = false)
    private Tenants tenant;

    private String role;

    public UUID getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Tenants getTenant() {
        return tenant;
    }

    public void setTenant(Tenants tenant) {
        this.tenant = tenant;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

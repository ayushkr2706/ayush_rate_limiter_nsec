package com.ayush.rateLimiterApp.tenantManagement.entity;

import com.ayush.rateLimiterApp.invitationManagement.enums.Role;
import com.ayush.rateLimiterApp.tenantManagement.enums.MembershipStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }
}

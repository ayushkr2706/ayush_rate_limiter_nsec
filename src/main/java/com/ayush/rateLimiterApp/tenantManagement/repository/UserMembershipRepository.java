package com.ayush.rateLimiterApp.tenantManagement.repository;

import com.ayush.rateLimiterApp.tenantManagement.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserMembershipRepository extends JpaRepository<UserMembership, UUID> {
}

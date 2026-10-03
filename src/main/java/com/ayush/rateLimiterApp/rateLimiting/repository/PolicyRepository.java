package com.ayush.rateLimiterApp.rateLimiting.repository;

import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PolicyRepository extends JpaRepository<Policies, UUID> {

    Optional<Policies> findByPolicyIdAndTenant_TenantId(UUID policyId, UUID tenantId);
}

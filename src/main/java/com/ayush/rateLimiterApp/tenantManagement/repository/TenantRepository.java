package com.ayush.rateLimiterApp.tenantManagement.repository;

import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenants, UUID> {

    Optional<Tenants> findByCompanyEmail(String companyEmail);
}

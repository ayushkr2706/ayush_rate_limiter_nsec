package com.ayush.rateLimiterApp.repository;

import com.ayush.rateLimiterApp.entity.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenants, UUID> {

}

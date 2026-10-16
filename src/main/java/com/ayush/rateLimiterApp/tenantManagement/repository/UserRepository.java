package com.ayush.rateLimiterApp.tenantManagement.repository;

import com.ayush.rateLimiterApp.tenantManagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);
}

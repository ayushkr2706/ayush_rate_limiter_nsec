package com.ayush.rateLimiterApp.apiCredentialManagement.repository;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiRepository extends JpaRepository<ApiCredentials, UUID> {

    Optional<ApiCredentials> findByKeyHash(String keyHash);
}

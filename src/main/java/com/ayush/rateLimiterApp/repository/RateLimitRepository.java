package com.ayush.rateLimiterApp.repository;

import com.ayush.rateLimiterApp.entity.RateLimitConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateLimitRepository extends JpaRepository<RateLimitConfigEntity, String> {
}
package com.ayush.rateLimiterApp.repository;

import com.ayush.rateLimiterApp.entity.Policies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PolicyRepository extends JpaRepository<Policies, UUID> {

}

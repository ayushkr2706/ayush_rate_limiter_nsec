package com.ayush.rateLimiterApp.rateLimiting.service;


import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.dto.ApiKeyInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LookUpService {

    private ApiRepository apiRepository;

    public LookUpService(ApiRepository apiRepository){
        this.apiRepository = apiRepository;
    }

    @Cacheable(cacheNames = "apiCache",
            key = "#keyHash", unless = "#result == null")
    public ApiKeyInfo findApiKey(String keyHash){
        ApiCredentials apiCredentials = apiRepository.findByKeyHash(keyHash)
                .orElse(null);

        if(apiCredentials == null){
            return null;
        }

        UUID tenantId = apiCredentials.getTenant().getTenantId();

        Boolean active = apiCredentials.getStatus().equalsIgnoreCase("active")
                && apiCredentials.getTenant().getStatus().equalsIgnoreCase("active");

        return new ApiKeyInfo(tenantId, active);
    }
}

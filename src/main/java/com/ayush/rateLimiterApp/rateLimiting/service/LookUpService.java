package com.ayush.rateLimiterApp.rateLimiting.service;


import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.dto.ApiKeyInfo;
import com.ayush.rateLimiterApp.rateLimiting.dto.PolicyInfo;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LookUpService {

    private final ApiRepository apiRepository;

    private final PolicyRepository policyRepository;

    public LookUpService(ApiRepository apiRepository,
                         PolicyRepository policyRepository){

        this.apiRepository = apiRepository;
        this.policyRepository = policyRepository;
    }

    @Cacheable(cacheNames = "apiCache",
            key = "#keyHash", unless = "#result == null")
    public ApiKeyInfo findApiKey(String keyHash){

        ApiCredentials fetchedApiCredentials = apiRepository.findByKeyHash(keyHash)
                .orElse(null);

        if(fetchedApiCredentials == null){
            return null;
        }

        UUID tenantId = fetchedApiCredentials.getTenant().getTenantId();

        Boolean active = fetchedApiCredentials.getStatus().equalsIgnoreCase("active")
                && fetchedApiCredentials.getTenant().getStatus().equalsIgnoreCase("active");

        return new ApiKeyInfo(tenantId, active);
    }

    @Cacheable(cacheNames = "policyCache", key = "#tenantId + '-' + #policyId", unless = "#result == null")
    public PolicyInfo findPolicy(UUID tenantId, UUID policyId){
    Policies fetchedPolicy = policyRepository.findByPolicyIdAndTenant_TenantId(policyId, tenantId)
            .orElse(null);

    if(fetchedPolicy == null){
        return null;
    }

    int capacity = fetchedPolicy.getCapacity();
    double refillRate = fetchedPolicy.getRefillRate();

    return new PolicyInfo(capacity, refillRate);
    }
}

package com.ayush.rateLimiterApp.rateLimiting.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.exception.ApiKeyNotFoundException;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterRequestDto;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterResponseDto;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import com.ayush.rateLimiterApp.rateLimiting.exception.PolicyNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantInactiveException;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RateLimiterService {

    private TokenBucketRateLimiter tokenBucketRateLimiter;

    private PolicyRepository policyRepository;

    private ApiRepository apiRepository;

    public RateLimiterService(TokenBucketRateLimiter tokenBucketRateLimiter,
                              ApiRepository apiRepository){
        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
        this.apiRepository = apiRepository;
    }

    public RateLimiterResponseDto rateLimit(RateLimiterRequestDto rateLimiterRequestDto, String authorization){

        UUID apiKey = UUID.fromString(authorization);
        ApiCredentials fetchedApiCredentials = apiRepository.findById(apiKey)
                .orElseThrow(() -> new ApiKeyNotFoundException("Tenant is not registered"));

        Tenants tenantTobeChecked = fetchedApiCredentials.getTenant();

        if(!tenantTobeChecked.getStatus().equals("active")){
            throw new TenantInactiveException("Tenant is not active");
        }
        UUID tenantId = tenantTobeChecked.getTenantId();
        UUID policyId = UUID.fromString(rateLimiterRequestDto.getPolicyId());
        Policies fetchedPolicy = policyRepository.findByIdAndTenantId(policyId, tenantId)
                .orElseThrow(() -> new PolicyNotFoundException("Tenant is not registered"));

        String identity = rateLimiterRequestDto.getUserIp();
        int capacity = fetchedPolicy.getCapacity();
        double refillRate = fetchedPolicy.getRefillRate();

        boolean isAllowed = tokenBucketRateLimiter.isAllowed(identity, capacity, refillRate);

        if(isAllowed){

        }
    }

}

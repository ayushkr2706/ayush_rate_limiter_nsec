package com.ayush.rateLimiterApp.rateLimiting.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimitResult;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterRequestDto;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterResponseDto;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import com.ayush.rateLimiterApp.rateLimiting.exception.PolicyNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantNotFoundException;
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
                              ApiRepository apiRepository,
                              PolicyRepository policyRepository){
        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
        this.apiRepository = apiRepository;
        this.policyRepository = policyRepository;
    }

    public RateLimiterResponseDto rateLimit(RateLimiterRequestDto rateLimiterRequestDto,
                                            String authorization){

        UUID apiKey = UUID.fromString(authorization);
        ApiCredentials fetchedApiCredentials = apiRepository.findById(apiKey)
                .orElseThrow(() -> new TenantNotFoundException("Tenant is not registered"));

        Tenants tenantTobeChecked = fetchedApiCredentials.getTenant();

        if(!tenantTobeChecked.getStatus().equals("active")){
            throw new TenantInactiveException("Tenant is not active");
        }

        UUID tenantId = tenantTobeChecked.getTenantId();
        UUID policyId = UUID.fromString(rateLimiterRequestDto.getPolicyId());
        Policies fetchedPolicy = policyRepository.findByPolicyIdAndTenant_TenantId(policyId, tenantId)
                .orElseThrow(() -> new PolicyNotFoundException("Tenant with policy id " + policyId.toString() +
                        " does not exist"));

        String userIp = rateLimiterRequestDto.getUserIp();
        int capacity = fetchedPolicy.getCapacity();
        double refillRate = fetchedPolicy.getRefillRate();

        String identity = tenantId.toString() + ":" + policyId.toString() + ":" + userIp;

        RateLimitResult result = tokenBucketRateLimiter.isAllowed(identity, capacity, refillRate);

        RateLimiterResponseDto response = new RateLimiterResponseDto();

        response.setLimit(capacity);
        response.setAllowed(result.getAllowed());
        response.setRetryAfter(result.getRetryAfter());
        response.setPolicyId(policyId.toString());
        response.setRemainingTokens(result.getTokens());

        if(result.getAllowed()){
          response.setMessage("Allowed");
        }
        else{
            response.setMessage("Rate Limit Exceeded");
        }

        return response;
    }

}

package com.ayush.rateLimiterApp.rateLimiting.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.apiCredentialManagement.utility.ApiKeyUtil;
import com.ayush.rateLimiterApp.rateLimiting.dto.ApiKeyInfo;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimitResult;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterRequestDto;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterResponseDto;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import com.ayush.rateLimiterApp.rateLimiting.exception.*;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RateLimiterService {

    private final TokenBucketRateLimiter tokenBucketRateLimiter;

    private final PolicyRepository policyRepository;

    private final ApiRepository apiRepository;

    private final LookUpService lookupService;

    public RateLimiterService(TokenBucketRateLimiter tokenBucketRateLimiter,
                              ApiRepository apiRepository,
                              PolicyRepository policyRepository,
                              LookUpService lookupService){

        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
        this.apiRepository = apiRepository;
        this.policyRepository = policyRepository;
        this.lookupService = lookupService;
    }

    public RateLimiterResponseDto rateLimit(RateLimiterRequestDto rateLimiterRequestDto,
                                            String authorization){

        String apiKey;

        try{
            apiKey = authorization.trim();
        }
        catch(IllegalArgumentException ex){
            throw new InvalidApiKeyException();
        }

        ApiKeyInfo apiKeyInfo = lookupService.findApiKey(ApiKeyUtil.hash(apiKey));

        if(apiKeyInfo == null){
            throw new InvalidApiKeyException();
        }

        if(!apiKeyInfo.active()){
            throw new TenantInactiveException("API key or account is not active");
        }

        ApiCredentials fetchedApiCredentials = apiRepository.findByKeyHash(ApiKeyUtil.hash(apiKey))
                .orElseThrow(() -> new TenantNotFoundException());

        Tenants tenantTobeChecked = fetchedApiCredentials.getTenant();

        if(!tenantTobeChecked.getStatus().equals("active")){
            throw new TenantInactiveException("Tenant is not active");
        }

        UUID tenantId = tenantTobeChecked.getTenantId();
        UUID policyId;
        try{
            policyId = UUID.fromString(rateLimiterRequestDto.getPolicyId().trim());
        }
        catch(IllegalArgumentException ex){
            throw new InvalidPolicyIdException("Invalid policy id");
        }

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

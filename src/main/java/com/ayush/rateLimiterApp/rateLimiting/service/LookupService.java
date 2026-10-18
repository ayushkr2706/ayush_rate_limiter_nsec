package com.ayush.rateLimiterApp.rateLimiting.service;


import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.dto.ApiKeyInfo;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import jakarta.persistence.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LookupService {

    private final ApiRepository apiRepository;

    private final PolicyRepository policyRepository;

    public LookupService(ApiRepository apiRepository,
                         PolicyRepository policyRepository){

        this.apiRepository = apiRepository;
        this.policyRepository = policyRepository;
    }

    //The name of the cache is apiKeys.
    //The key of the cache is the keyHash received in the method parameter.
    //Do not cache the result if it is null
    @Cacheable(cacheNames = "apiKeys", key="#keyHash", unless = "#result == null")
    public ApiKeyInfo findApiKey(String keyHash){
        return apiRepository.findByKeyHash(keyHash)
                .map(credential -> new ApiKeyInfo(
                        credential.getTenant().getTenantId(),
                        "active".equals(credential.getStatus())
                        && "active".equals(credential.getTenant().getStatus())
                )).orElse(null);
    }
}

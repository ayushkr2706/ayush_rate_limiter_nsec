package com.ayush.rateLimiterApp.PolicyManagement.service;

import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyRequestDto;
import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyResponseDto;
import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.entity.Policies;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantNotFoundException;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PolicyService {

    private PolicyRepository policyRepository;

    private ApiRepository apiRepository;

    public PolicyService(PolicyRepository policyRepository,
                         ApiRepository apiRepository){
        this.policyRepository = policyRepository;
        this.apiRepository = apiRepository;
    }

    public PolicyResponseDto createPolicy(PolicyRequestDto policyRequestDto,
                                          String authorization){

        UUID apiKey = UUID.fromString(authorization);

        ApiCredentials apiCredential = apiRepository.findById(apiKey)
                .orElseThrow(() -> new TenantNotFoundException());

        Tenants tenant = apiCredential.getTenant();

        Policies policyToBeSaved = mapToPolicyEntity(policyRequestDto);
        policyToBeSaved.setTenant(tenant);

        Policies savedPolicy = policyRepository.save(policyToBeSaved);
        return mapToPolicyResponseDto(savedPolicy);
    }

    public Policies mapToPolicyEntity(PolicyRequestDto policyRequestDto){
        Policies policy = new Policies();
        policy.setRefillRate(policyRequestDto.getRefillRate());
        policy.setCapacity(policyRequestDto.getCapacity());
        return policy;
    }

    public PolicyResponseDto mapToPolicyResponseDto(Policies savedPolicy){

        PolicyResponseDto policyResponseDto = new PolicyResponseDto();
        policyResponseDto.setPolicyId(savedPolicy.getId().toString());
        policyResponseDto.setCapacity(savedPolicy.getCapacity());
        policyResponseDto.setRefillRate(savedPolicy.getRefillRate());
        return policyResponseDto;
    }
}

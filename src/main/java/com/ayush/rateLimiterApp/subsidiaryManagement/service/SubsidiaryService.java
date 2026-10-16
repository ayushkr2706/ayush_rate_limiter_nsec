package com.ayush.rateLimiterApp.subsidiaryManagement.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.apiCredentialManagement.utility.ApiKeyUtil;
import com.ayush.rateLimiterApp.rateLimiting.exception.InvalidApiKeyException;
import com.ayush.rateLimiterApp.rateLimiting.exception.TenantNotFoundException;
import com.ayush.rateLimiterApp.subsidiaryManagement.dto.SubsidiaryRequestDto;
import com.ayush.rateLimiterApp.subsidiaryManagement.dto.SubsidiaryResponseDto;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import com.ayush.rateLimiterApp.tenantManagement.repository.TenantRepository;
import com.ayush.rateLimiterApp.tenantManagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SubsidiaryService {

    private final ApiRepository apiRepository;

    private final TenantRepository tenantRepository;

    public SubsidiaryService(ApiRepository apiRepository,
                             TenantRepository tenantRepository){

        this.apiRepository = apiRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public SubsidiaryResponseDto createSubsidiary(SubsidiaryRequestDto subsidiaryRequestDto,
                                                  String authorization){
        String plainApiKey;
        try{
            plainApiKey = authorization.trim();
        }
        catch(IllegalArgumentException ex){
            throw new InvalidApiKeyException();
        }

        String keyHash = ApiKeyUtil.hash(plainApiKey);
        ApiCredentials apiCredential = apiRepository.findByKeyHash(keyHash)
                .orElseThrow(() -> new TenantNotFoundException());

        Tenants parentTenant = apiCredential.getTenant();
        String parentTenantId = String.valueOf(parentTenant.getTenantId());

        Tenants subsidiaryToBeSaved = mapToTenantsEntity(subsidiaryRequestDto, parentTenantId);
        Tenants savedSubsidiary = tenantRepository.saveAndFlush(subsidiaryToBeSaved);

        String subsidiaryApiKey = ApiKeyUtil.generate();
        apiRepository.save(mapToApiCredentialsEntity(savedSubsidiary, subsidiaryApiKey));

        SubsidiaryResponseDto response = mapToSubsidiaryResponseDto(savedSubsidiary);
        response.setApiKey(subsidiaryApiKey);
        response.setCreatedAt(LocalDateTime.now());
        response.setParentCompanyName(parentTenant.getCompanyName());
        return response;
    }

    private Tenants mapToTenantsEntity(SubsidiaryRequestDto subsidiaryRequestDto,
                                       String parentTenantId){

        Tenants tenant = new Tenants();
        tenant.setParentTenantId(UUID.fromString(parentTenantId));
        tenant.setCompanyName(subsidiaryRequestDto.getCompanyName());
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setStatus("active");
        return tenant;
    }

    private ApiCredentials mapToApiCredentialsEntity(Tenants savedSubsidiary, String subsidiaryApiKey){

        ApiCredentials apiCredential = new ApiCredentials();
        apiCredential.setKeyHash(ApiKeyUtil.hash(subsidiaryApiKey));
        apiCredential.setKeyHint(subsidiaryApiKey.substring(0, 12));
        apiCredential.setTenant(savedSubsidiary);
        apiCredential.setCreatedAt(LocalDateTime.now());
        return apiCredential;
    }

    private SubsidiaryResponseDto mapToSubsidiaryResponseDto(Tenants subsidiary){

        SubsidiaryResponseDto response = new SubsidiaryResponseDto();
        response.setCompanyName(subsidiary.getCompanyName());
        response.setStatus(subsidiary.getStatus());
        return response;
    }
}

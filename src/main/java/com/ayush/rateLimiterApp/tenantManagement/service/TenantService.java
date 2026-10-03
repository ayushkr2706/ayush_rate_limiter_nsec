package com.ayush.rateLimiterApp.tenantManagement.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationRequestDto;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationResponseDto;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateTenantException;
import com.ayush.rateLimiterApp.tenantManagement.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TenantService {

    private TenantRepository tenantRepository;

    private ApiRepository apiRepository;

    public TenantService(TenantRepository tenantRepository, ApiRepository apiRepository){
        this.tenantRepository = tenantRepository;
        this.apiRepository = apiRepository;
    }

    public RegistrationResponseDto registerTenant(RegistrationRequestDto registrationRequestDto){

        Optional <Tenants> fetchedTenantOptional = tenantRepository.
                findByCompanyEmail(registrationRequestDto.getCompanyEmail());

        if(fetchedTenantOptional.isPresent()){
            throw new DuplicateTenantException("Tenant with emailId "
                      + registrationRequestDto.getCompanyEmail() + " already exists");
        }

        Tenants tenant = mapToTenantsEntity(registrationRequestDto);
        Tenants savedTenant = tenantRepository.save(tenant);
        ApiCredentials apiCredential = apiRepository.save(mapToApiCredentialEntity(savedTenant));
        RegistrationResponseDto response =  mapToRegistrationResponseDto(tenant);
        response.setCreatedAt(LocalDateTime.now());
        response.setApiKey(apiCredential.getApiKey());
        return response;
    }

    private ApiCredentials mapToApiCredentialEntity(Tenants tenant) {
        ApiCredentials apiCredential = new ApiCredentials();
        apiCredential.setTenant(tenant);
        apiCredential.setCreatedAt(LocalDateTime.now());
        return apiCredential;
    }

    private RegistrationResponseDto mapToRegistrationResponseDto(Tenants tenant) {

        RegistrationResponseDto response = new RegistrationResponseDto();
        response.setStatus(tenant.getStatus());
        return response;
    }

    private Tenants mapToTenantsEntity(RegistrationRequestDto registrationRequestDto) {

        Tenants tenant = new Tenants();
        tenant.setCompanyName(registrationRequestDto.getCompanyName());
        tenant.setCompanyEmail(registrationRequestDto.getCompanyEmail());
        tenant.setPassword(registrationRequestDto.getPassword());
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setStatus("active");
        return tenant;
    }

}

package com.ayush.rateLimiterApp.tenantManagement.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.rateLimiting.repository.PolicyRepository;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationRequestDto;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationResponseDto;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateTenantException;
import com.ayush.rateLimiterApp.tenantManagement.repository.TenantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    private final ApiRepository apiRepository;

    private final PolicyRepository policyRepository;

    private final PasswordEncoder passwordEncoder;

    public TenantService(TenantRepository tenantRepository,
                         ApiRepository apiRepository,
                         PolicyRepository policyRepository,
                         PasswordEncoder passwordEncoder){
        this.tenantRepository = tenantRepository;
        this.apiRepository = apiRepository;
        this.policyRepository = policyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegistrationResponseDto registerTenant(RegistrationRequestDto registrationRequestDto){

        String email = registrationRequestDto.getCompanyEmail()
                .trim().toLowerCase(java.util.Locale.ROOT);

        Optional <Tenants> fetchedTenantOptional = tenantRepository.
                findByCompanyEmail(email);

        if(fetchedTenantOptional.isPresent()){
            throw new DuplicateTenantException("Tenant with emailId "
                      + email + " already exists");
        }

        Tenants tenant = mapToTenantsEntity(registrationRequestDto, email);
        Tenants savedTenant;
        try{
            savedTenant = tenantRepository.saveAndFlush(tenant);
        }
        catch(DataIntegrityViolationException ex){
            throw new DuplicateTenantException("Tenant with email id " + email + " already exists");
        }


        ApiCredentials savedApiCredential = apiRepository.save(mapToApiCredentialEntity(savedTenant));
        RegistrationResponseDto response =  mapToRegistrationResponseDto(tenant);
        response.setCreatedAt(LocalDateTime.now());
        response.setApiKey(savedApiCredential.getApiKey().toString());
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

    private Tenants mapToTenantsEntity(RegistrationRequestDto registrationRequestDto, String email) {

        Tenants tenant = new Tenants();
        tenant.setCompanyName(registrationRequestDto.getCompanyName());
        tenant.setCompanyEmail(email);
        tenant.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setStatus("active");
        return tenant;
    }

}

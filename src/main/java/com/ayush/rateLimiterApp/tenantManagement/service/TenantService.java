package com.ayush.rateLimiterApp.tenantManagement.service;

import com.ayush.rateLimiterApp.apiCredentialManagement.entity.ApiCredentials;
import com.ayush.rateLimiterApp.apiCredentialManagement.repository.ApiRepository;
import com.ayush.rateLimiterApp.apiCredentialManagement.utility.ApiKeyUtil;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationRequestDto;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationResponseDto;
import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import com.ayush.rateLimiterApp.tenantManagement.entity.UserMembership;
import com.ayush.rateLimiterApp.tenantManagement.entity.Users;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateTenantException;
import com.ayush.rateLimiterApp.tenantManagement.exception.DuplicateUserException;
import com.ayush.rateLimiterApp.tenantManagement.repository.TenantRepository;
import com.ayush.rateLimiterApp.tenantManagement.repository.UserMembershipRepository;
import com.ayush.rateLimiterApp.tenantManagement.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    private final ApiRepository apiRepository;

    private final UserRepository userRepository;

    private final UserMembershipRepository userMembershipRepository;

    private final PasswordEncoder passwordEncoder;

    public TenantService(TenantRepository tenantRepository,
                         ApiRepository apiRepository,
                         PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         UserMembershipRepository userMembershipRepository){

        this.tenantRepository = tenantRepository;
        this.apiRepository = apiRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMembershipRepository = userMembershipRepository;
    }

    @Transactional
    public RegistrationResponseDto registerTenant(RegistrationRequestDto registrationRequestDto){

        String email = registrationRequestDto.getEmail()
                .trim().toLowerCase(java.util.Locale.ROOT);

        Optional <Users> fetchedUserOptional = userRepository.
                findByEmail(email);

        if(fetchedUserOptional.isPresent()){
            throw new DuplicateUserException("User with emailId "
                      + email + " already exists");
        }

        Tenants tenant = mapToTenantsEntity(registrationRequestDto);
        Users user = mapToUserEntity(registrationRequestDto, email);
        Tenants savedTenant;
        try{
            savedTenant = tenantRepository.saveAndFlush(tenant);
        }
        catch(DataIntegrityViolationException ex){
            throw new DuplicateTenantException("Tenant already exists");
        }

        Users savedUser;
        try{
            savedUser = userRepository.saveAndFlush(user);
        }
        catch (DataIntegrityViolationException ex){
            throw new DuplicateUserException("User with email id " + email + " already exists");
        }

        UserMembership userMembership = mapToUserMembershipEntity(savedTenant, savedUser);
        UserMembership savedUserMembership = userMembershipRepository.saveAndFlush(userMembership);

        String plainApiKey = ApiKeyUtil.generate();
        apiRepository.save(mapToApiCredentialEntity(savedTenant, plainApiKey));

        RegistrationResponseDto response =  mapToRegistrationResponseDto(savedTenant);
        response.setCreatedAt(LocalDateTime.now());
        response.setApiKey(plainApiKey);
        return response;
    }

    private UserMembership mapToUserMembershipEntity(Tenants savedTenant, Users savedUser) {

        UserMembership userMembership = new UserMembership();
        userMembership.setUser(savedUser);
        userMembership.setTenant(savedTenant);
        userMembership.setRole("admin");
        return userMembership;
    }

    private Users mapToUserEntity(RegistrationRequestDto registrationRequestDto, String email) {

        Users user = new Users();

        user.setFirstName(registrationRequestDto.getFirstName());
        user.setLastName(registrationRequestDto.getLastName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus("active");
        return user;
    }

    private ApiCredentials mapToApiCredentialEntity(Tenants tenant, String plainApiKey) {
        ApiCredentials apiCredential = new ApiCredentials();
        apiCredential.setKeyHash(ApiKeyUtil.hash(plainApiKey));
        apiCredential.setKeyHint(plainApiKey.substring(0, 12));
        apiCredential.setTenant(tenant);
        apiCredential.setCreatedAt(LocalDateTime.now());
        apiCredential.setStatus("active");
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
        tenant.setParentTenantId(null);
        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setStatus("active");
        return tenant;
    }

}

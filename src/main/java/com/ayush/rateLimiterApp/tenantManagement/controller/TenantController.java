package com.ayush.rateLimiterApp.tenantManagement.controller;

import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationRequestDto;
import com.ayush.rateLimiterApp.tenantManagement.dto.RegistrationResponseDto;
import com.ayush.rateLimiterApp.tenantManagement.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class TenantController {

    //The Tenant Controller is responsible only for the registration of the tenant
    //and the policy should also be provided by the tenant during registration.

    private TenantService tenantService;

    public TenantController(TenantService tenantService){
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> registerTenant(@RequestBody RegistrationRequestDto
                                                                              registrationRequestDto){
        RegistrationResponseDto response = tenantService.registerTenant(registrationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

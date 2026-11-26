package com.ayush.rateLimiterApp.PolicyManagement.controller;

import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyRequestDto;
import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyResponseDto;
import com.ayush.rateLimiterApp.PolicyManagement.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService){
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<PolicyResponseDto> createPolicy(@Valid @RequestBody PolicyRequestDto policyRequestDto,
                                                          @RequestHeader("Authorization") String authorization){

        PolicyResponseDto response = policyService.createPolicy(policyRequestDto, authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}

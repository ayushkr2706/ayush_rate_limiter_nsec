package com.ayush.rateLimiterApp.PolicyManagement.controller;

import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyRequestDto;
import com.ayush.rateLimiterApp.PolicyManagement.dto.PolicyResponseDto;
import com.ayush.rateLimiterApp.PolicyManagement.service.PolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private PolicyService policyService;

    public PolicyController(PolicyService policyService){
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<PolicyResponseDto> createPolicy(@RequestBody PolicyRequestDto policyRequestDto,
                                                          @RequestHeader("Authorization") String authorization){

        PolicyResponseDto response = policyService.createPolicy(policyRequestDto, authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}

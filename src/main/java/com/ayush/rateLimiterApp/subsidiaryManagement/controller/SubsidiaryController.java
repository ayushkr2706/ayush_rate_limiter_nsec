package com.ayush.rateLimiterApp.subsidiaryManagement.controller;

import com.ayush.rateLimiterApp.subsidiaryManagement.dto.SubsidiaryRequestDto;
import com.ayush.rateLimiterApp.subsidiaryManagement.dto.SubsidiaryResponseDto;
import com.ayush.rateLimiterApp.subsidiaryManagement.service.SubsidiaryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subsidiary")
public class SubsidiaryController {

    private final SubsidiaryService subsidiaryService;

    public SubsidiaryController(SubsidiaryService subsidiaryService){
        this.subsidiaryService = subsidiaryService;
    }

    @PostMapping
    public ResponseEntity<SubsidiaryResponseDto> createSubsidiary(@Valid
                                                                  @RequestBody SubsidiaryRequestDto subsidiaryRequestDto,
                                                                  @RequestHeader("Authorization") String authorization
                                                                  ){
        SubsidiaryResponseDto response = subsidiaryService.createSubsidiary(subsidiaryRequestDto, authorization);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

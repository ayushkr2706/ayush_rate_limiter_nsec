package com.ayush.rateLimiterApp.subsidiaryManagement.controller;

import com.ayush.rateLimiterApp.subsidiaryManagement.service.SubsidiaryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subsidiary")
public class SubsidiaryController {

    private final SubsidiaryService subsidiaryService;

    public SubsidiaryController(SubsidiaryService subsidiaryService){
        this.subsidiaryService = subsidiaryService;
    }

    public ResponseEntity<SubsidiaryResponseDto> createSubsidiary(@Valid
                                                                  @RequestBody SubsidiaryRequestDto subsidiaryRequestDto,
                                                                  @RequestHeader("Authorization") String authorization
                                                                  ){
        SubsidiaryResponseDto response =
    }
}

package com.ayush.rateLimiterApp.rateLimiting.controller;

import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterRequestDto;
import com.ayush.rateLimiterApp.rateLimiting.dto.RateLimiterResponseDto;
import com.ayush.rateLimiterApp.rateLimiting.service.RateLimiterService;
import com.ayush.rateLimiterApp.rateLimiting.service.TokenBucketRateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rate-limit")
public class RateLimiterController {

    private RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService){
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping
    public ResponseEntity<RateLimiterResponseDto> isAllowed(@RequestBody RateLimiterRequestDto rateLimiterRequestDto,
                                                            @RequestHeader("Authorization") String authorization ){

        RateLimiterResponseDto response = rateLimiterService.rateLimit(rateLimiterRequestDto, authorization);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

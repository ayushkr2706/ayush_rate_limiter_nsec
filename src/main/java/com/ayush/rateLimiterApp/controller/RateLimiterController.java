package com.ayush.rateLimiterApp.controller;

import com.ayush.rateLimiterApp.entity.RateLimitConfigEntity;
import com.ayush.rateLimiterApp.service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RateLimiterController {


    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/config")
    public ResponseEntity<String> addConfig(@RequestBody RateLimitConfigEntity configEntity){
        rateLimiterService.saveConfig(configEntity);
        return ResponseEntity.ok("config added");
    }

    @GetMapping("/config/{userId}")
    public ResponseEntity<RateLimitConfigEntity>getConfig(@PathVariable String userId){
        return ResponseEntity.ok(rateLimiterService.getConfig(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> Check(@PathVariable String userId){

         boolean allowed = rateLimiterService.isAllowed(userId);
         if(allowed) {
             return ResponseEntity.ok("Success");
         }
         else return ResponseEntity.status(429).body("Rate Limit Exceeded");

    }

}

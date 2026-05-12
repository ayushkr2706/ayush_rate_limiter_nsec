package com.ayush.rateLimiterApp.controller;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;


    @PostMapping("/check")
    public String Check(@RequestParam int userId){

         boolean allowed = rateLimiterService.isAllowed(userId);
         if(allowed) {
             return "Success";
         }
         else return "Rate Limit Exceeded";

    }

}

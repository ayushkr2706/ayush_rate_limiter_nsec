package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private final List<RateLimiterStrategy> strategies;

    public RateLimiterService(List<RateLimiterStrategy> strategies) {
      this.strategies = strategies;
    }

    public Boolean isAllowed(int userId) {
      for(RateLimiterStrategy strategy : strategies) {
        Boolean allowed = strategy.isAllowed(userId);
        if (!allowed) {
          System.out.println("Blocked by: " + strategy.getClass().getSimpleName());
          return false;
        }
      }
      return true;
    }
  }

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
//    private final List<RateLimiterStrategy> strategies;
//
//    public RateLimiterService(List<RateLimiterStrategy> strategies) {
//      this.strategies = strategies;
//    }
    private final Map<String, RateLimiterStrategy> strategyMap;
    public RateLimiterService(Map<String, RateLimiterStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }



    public Boolean isAllowed(int userId, String strategyType) {
      RateLimiterStrategy strategy = strategyMap.get(strategyType);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid strategy: " + strategyType);
        }
      return strategy.isAllowed(userId);
    }
  }

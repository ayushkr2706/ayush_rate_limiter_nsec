package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import com.ayush.rateLimiterApp.entity.RateLimitConfigEntity;
import com.ayush.rateLimiterApp.repository.RateLimitRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
private final RateLimitRepository repository;


    private final Map<String, RateLimiterStrategy> strategyMap;
    public RateLimiterService(Map<String, RateLimiterStrategy> strategyMap, RateLimitRepository repository) {
        this.strategyMap = strategyMap;
        this.repository = repository;
    }

   // private final Map<Integer, RateLimitConfig> userConfigMap = new ConcurrentHashMap<>();

//    @PostConstruct
//    public void init() {
//        userConfigMap.put(1, new RateLimitConfig("slidingWindowStrategy", 2,10000));
//        userConfigMap.put(2, new RateLimitConfig("fixedWindowStrategy", 1, 5000));
//    }

//    @PostConstruct
//    public void init() {
//        repository.save(new RateLimitConfigEntity(1,"slidingWindowStrategy", 2, 10000));
//        repository.save(new RateLimitConfigEntity(2,"fixedWindowStrategy", 1, 5000));
//    }

    public void saveConfig(RateLimitConfigEntity config) {
        repository.save(config);
    }

    public RateLimitConfigEntity getConfig(String userId) {
        return repository.findById(userId).orElse(null);
    }

    public Boolean isAllowed(String userId) {

//        RateLimitConfig config = userConfigMap.get(userId);

        RateLimitConfigEntity rateLimitConfigEntity =
                repository.findById(userId).orElse(null);

//        if (config == null) {
//            config = new RateLimitConfig("slidingWindowStrategy", 2, 10000);
//
//        }

        if(rateLimitConfigEntity == null){
            rateLimitConfigEntity = new RateLimitConfigEntity();
            rateLimitConfigEntity.setLimit(2);
            rateLimitConfigEntity.setStrategyType("slidingWindowStrategy");
            rateLimitConfigEntity.setWindowSize(10000);
        }

        RateLimiterStrategy strategy = strategyMap.get(rateLimitConfigEntity.getStrategyType());
//      return strategy.isAllowed(userId, config);

        if (strategy == null) {
            throw new IllegalArgumentException("Strategy not found");
        }

        RateLimitConfig config = new RateLimitConfig(rateLimitConfigEntity.getStrategyType(),  rateLimitConfigEntity.getLimit(), rateLimitConfigEntity.getWindowSize());
        System.out.println("User : "+userId);
        System.out.println("Strategy : "+ rateLimitConfigEntity.getStrategyType());
        System.out.println("Window : "+ rateLimitConfigEntity.getWindowSize());
        System.out.println("Limit : "+rateLimitConfigEntity.getLimit());
        return strategy.isAllowed(userId, config);
    }
  }

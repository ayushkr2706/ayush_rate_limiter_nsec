package com.ayush.rateLimiterApp.service;

import com.ayush.rateLimiterApp.RateLimiterStrategy;
import com.ayush.rateLimiterApp.config.RateLimitConfig;
import com.ayush.rateLimiterApp.entity.RateLimitConfigEntity;
import com.ayush.rateLimiterApp.repository.RateLimitRepository;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service // mark this as service class.
public class RateLimiterService {

    private final RateLimitRepository repository;
    private final Map<String, RateLimiterStrategy> strategyMap;

    public RateLimiterService(RateLimitRepository repository, Map<String, RateLimiterStrategy> strategyMap) {
        this.repository = repository;
        this.strategyMap = strategyMap;
    }

    public void saveConfig(RateLimitConfigEntity entity) {
        repository.save(entity);
    }

    public RateLimitConfigEntity getConfig(String userId) {
        return repository.findById(userId).orElse(null);
    }

    public boolean isAllowed(String userId) {

        RateLimitConfigEntity entity = repository.findById(userId).orElse(null);

        if (entity == null) { // (if no user exist)

            entity = new RateLimitConfigEntity();
            entity.setUserId(userId);
            entity.setStrategyType("FixedWindowStrategy");
            entity.setLimit(3);
            entity.setWindowSize(5);

        }

        RateLimiterStrategy strategy = strategyMap.get(entity.getStrategyType());

        RateLimitConfig config = new RateLimitConfig(entity.getStrategyType(), entity.getLimit(),
                entity.getWindowSize());

        System.out.println("userId : " + userId);
        System.out.println("strategyType : " + entity.getStrategyType());
        System.out.println("limit : " + entity.getLimit());
        System.out.println("windiowSize : " + entity.getWindowSize());
        System.out.println();

        return strategy.isAllowed(userId, config);
    }
}

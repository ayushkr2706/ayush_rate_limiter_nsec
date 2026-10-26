package com.ayush.rateLimiterApp.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager createCacheManager(){       //CacheManager is an interface provided by Spring cache module
                                                    //to make our application communicate with the caffeine cache.

        CaffeineCacheManager cacheManager = new CaffeineCacheManager(); //CaffeineCacheManager class is an
                                                                        //implementation class of CacheManager. This
                                                                        //is also provided by Spring cache module.

        cacheManager.setCacheNames(List.of("apiCache", "policyCache"));

        Caffeine<Object, Object> apiCacheBuilder = Caffeine.newBuilder();   //apiCache

        Caffeine<Object, Object> policyCacheBuilder = Caffeine.newBuilder();  //policyCache

        apiCacheBuilder.expireAfterWrite(Duration.ofSeconds(30))
                .maximumSize(10000);

        policyCacheBuilder.expireAfterWrite(Duration.ofSeconds(300))
                .maximumSize(2000);

        cacheManager.registerCustomCache(
                "apiCache", apiCacheBuilder.build()
        );

        cacheManager.registerCustomCache(
                "policyCache", policyCacheBuilder.build()
        );
        return cacheManager;

    }
}

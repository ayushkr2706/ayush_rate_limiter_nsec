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

    //CacheManager is an interface provided by Spring cache module
    //to make our application communicate with the caffeine cache.
    @Bean
    public CacheManager createCacheManager(){

        //CaffeineCacheManager class is an implementation class of CacheManager.
        //This is also provided by Spring Cache module.
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        //defining the names of the caches that our CacheManager will manage.
        cacheManager.setCacheNames(List.of("apiCache", "policyCache"));

        //creating builders for configuring a cache
        Caffeine<Object, Object> apiCacheBuilder = Caffeine.newBuilder().recordStats();   //apiCache

        Caffeine<Object, Object> policyCacheBuilder = Caffeine.newBuilder().recordStats();  //policyCache

        //setting the properties of apiCacheBuilder
        apiCacheBuilder.expireAfterWrite(Duration.ofSeconds(60))
                .maximumSize(10000);

        //setting the configurations of policyCacheBuilder
        policyCacheBuilder.expireAfterWrite(Duration.ofSeconds(300))
                .maximumSize(2000);


        //creating the actual Caffeine Cache and registering it with CacheManager
        cacheManager.registerCustomCache(
                "apiCache", apiCacheBuilder.build()
        );

        cacheManager.registerCustomCache(
                "policyCache", policyCacheBuilder.build()
        );

        return cacheManager;

    }
}

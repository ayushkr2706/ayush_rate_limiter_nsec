package com.ayush.rateLimiterApp.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager createCacheManager(){       //CacheManager is an interface provided by Spring cache module
                                                    //to make our application communicate with the caffeine cache.

        CaffeineCacheManager cacheManager = new CaffeineCacheManager(); //CaffeineCacheManager class is an
                                                                        //implementation class of CacheManager. This
                                                                        //is also provided by Spring cache module.

        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder();

        caffeineBuilder.expireAfterWrite(Duration.ofSeconds(120))
                .maximumSize(10000);

        cacheManager.setCaffeine(caffeineBuilder);
        return cacheManager;

    }
}

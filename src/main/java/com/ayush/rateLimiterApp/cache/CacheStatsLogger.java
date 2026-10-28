
package com.ayush.rateLimiterApp.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CacheStatsLogger {

    private final CacheManager cacheManager;

    public CacheStatsLogger(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> result = new LinkedHashMap<>();

        addCacheStats(result, "apiCache");
        addCacheStats(result, "policyCache");

        return result;
    }

    private void addCacheStats(
            Map<String, Object> result, String cacheName) {

        org.springframework.cache.Cache springCache =
                cacheManager.getCache(cacheName);

        if (springCache instanceof CaffeineCache caffeineCache) {
            Cache<Object, Object> nativeCache =
                    caffeineCache.getNativeCache();

            var stats = nativeCache.stats();

            Map<String, Object> details = new LinkedHashMap<>();
            details.put("hitCount", stats.hitCount());
            details.put("missCount", stats.missCount());
            details.put("hitRate", stats.hitRate());
            details.put("evictionCount", stats.evictionCount());
            details.put("loadSuccessCount", stats.loadSuccessCount());
            details.put("loadFailureCount", stats.loadFailureCount());

            result.put(cacheName, details);
        }
    }
}

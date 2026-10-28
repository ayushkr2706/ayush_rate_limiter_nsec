
package com.ayush.rateLimiterApp.cache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/debug")
public class CacheStatsController {

    private final CacheStatsLogger cacheStatsLogger;

    public CacheStatsController(CacheStatsLogger cacheStatsLogger) {
        this.cacheStatsLogger = cacheStatsLogger;
    }

    @GetMapping("/cache-stats")
    public Map<String, Object> getCacheStats() {
        return cacheStatsLogger.getStats();
    }
}

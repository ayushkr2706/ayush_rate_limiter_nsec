
package com.ayush.rateLimiterApp.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheStatsTest {

    @Autowired
    private CacheStatsLogger cacheStatsLogger;

    @Test
    void printCacheStatistics() {
        cacheStatsLogger.printStats();
    }
}

package com.daimon.recruiting.candidate.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CANDIDATES_CACHE = "candidates";

    @Value("${cache.time-to-live: 10}")
    private int cacheTime;

    @Bean
    public Config config() {
        var config = new Config();
        var mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(cacheTime);
        config.getMapConfigs().put(CANDIDATES_CACHE, mapConfig);
        return config;

    }
}
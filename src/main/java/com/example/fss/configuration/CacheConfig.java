package com.example.fss.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cachebale annotation by default does not support TTL value
 * So using Guava library to add expiry time to the cache
 */
@Configuration
public class CacheConfig {
    public static final String PRICE_CACHE = "priceCache";

    @Bean
    public Cache cacheOne() {
        return new GuavaCache(PRICE_CACHE, CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build());
    }
}

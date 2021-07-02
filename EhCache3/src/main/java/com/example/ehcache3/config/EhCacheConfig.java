package com.example.ehcache3.config;

import com.example.ehcache3.controller.CacheListener;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryEventFilter;
import java.time.Duration;

/**
 * https://azdanov.js.org/posts/2020/09/configuring-ehcache-3-and-event-listeners-in-spring-boot/
 */



@EnableCaching
@Configuration
public class EhCacheConfig {

    private static final Factory<? extends CacheEntryEventFilter<Integer, Integer>> NO_FILTER = null;
    private static final boolean IS_OLD_VALUE_REQUIRED = false;
    private static final boolean IS_SYNCHRONOUS = true;

    private final javax.cache.configuration.Configuration<Integer, Integer> jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                    CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, Integer.class, ResourcePoolsBuilder.heap(100))
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)))
                            .build()
            );

    CacheEntryListenerConfiguration<Integer, Integer> listenerConfiguration =
            new MutableCacheEntryListenerConfiguration<>(
                    FactoryBuilder.factoryOf(CacheListener.class),
                    NO_FILTER,
                    IS_OLD_VALUE_REQUIRED,
                    IS_SYNCHRONOUS);

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, "worker1");
            createCache(cm, "worker2");
        };
    }

    private void createCache(CacheManager cm, String cacheName) {
        Cache<Integer, Integer> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration)
                    .registerCacheEntryListener(listenerConfiguration);
        }
    }
}

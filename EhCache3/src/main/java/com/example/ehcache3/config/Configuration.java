package com.example.ehcache3.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

@org.springframework.context.annotation.Configuration
public class Configuration {

    public void resource(){
        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(2,MemoryUnit.GB)).build();
    }
}

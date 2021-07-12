package com.example.ehcache3.util;

import com.example.ehcache3.model.Employee;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
//import org.ehcache.xml.model.TimeUnit;

import java.io.File;
import java.util.concurrent.TimeUnit;


/**
 * https://velog.io/@geunwoobaek/Spring-Encache
 */
public class PersistentCacheManagerUtil {

    public static void util(){

//        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("employeeCache",
//                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Employee.class,
//                        ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                .heap(10, EntryUnit.ENTRIES)
//                                .offheap(10, MemoryUnit.MB))
//        )
//                .build(true);
//
//        cacheManager.close();

        PersistentCacheManager persistentCacheManager =
                CacheManagerBuilder.newCacheManagerBuilder()
                        .with(CacheManagerBuilder.persistence(getStoragePath()
                                + File.separator
                                + "squaredValue"))
                        .withCache("persistent-cache", CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(Long.class, Employee.class,
                                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                                .heap(10, EntryUnit.ENTRIES)
                                                .disk(10, MemoryUnit.MB, true))
                        )
                        .build(true);

        persistentCacheManager.close();

//        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(2, MemoryUnit.GB)).build();

    }

    private static String getStoragePath() {

        return "${java.io.tmpdir}/ehcache-data";
    }


    // 이 캐시에서 모든 데이터는 60 초 동안 유지되며 그 시간이 지나면 메모리에서 삭제
    public void expire(){
        CacheConfiguration<Integer, Integer> cacheConfiguration
                = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, Integer.class,
                        ResourcePoolsBuilder.heap(100))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(60,
                        TimeUnit.SECONDS))).build();
    }


}

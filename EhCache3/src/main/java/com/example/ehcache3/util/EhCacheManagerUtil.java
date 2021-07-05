package com.example.ehcache3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;


/**
 * https://github.com/BangShinChul/Spring-boot-ehcache-demo-project/blob/master/src/main/java/com/example/ehcache/demo/controller/HrController.java
 */
@Component
public class EhCacheManagerUtil {

    @Autowired
    CacheManager cacheManager;

//    private EhCacheManagerUtil() {
//        try {
//            CachingProvider provider = Caching.getCachingProvider();
//            cacheManager = provider.getCacheManager(
//                    new ClassPathResource("ehcache.xml").getURI(),
//                    getClass().getClassLoader());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // cache를 이름별로 clear하는 메서드
    public void clearCache(String cacheName) {
        try {
            cacheManager.getCache(cacheName).clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 전체 cache를 clear하는 메서드
    public void clearCacheAll() {
        Iterable<String> cacheNames = cacheManager.getCacheNames();
        try {
            for (String cacheName : cacheNames) {
                cacheManager.getCache(cacheName).clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.example.ehcache3.controller;

//import com.example.ehcache3.util.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * https://medium.com/finda-tech/spring-%EB%A1%9C%EC%BB%AC-%EC%BA%90%EC%8B%9C-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-ehcache-4b5cba8697e0
 * https://www.javadevjournal.com/spring-boot/spring-boot-with-ehcache/
 */
@RestController
public class EhCacheController {

    private static final Logger logger = LoggerFactory.getLogger(EhCacheController.class);


//    @Autowired
//    EhCacheManager cacheManager;

    @Cacheable(value = "squareCache", key = "#number", condition = "#number > 10")
    @GetMapping(value = "/ehcahe/{number}")
    public String getSquare(@PathVariable Long number){
        logger.info("====== EhCacheController - getSquare =====");
        return String.format("{\"square\":%s|", square(number));
    }

    @Cacheable(value = "squareCache", key = "#number", condition = "#number > 10")
    public BigDecimal square(Long number){
        BigDecimal square = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(number));
        logger.info("square of {} is {}", number, square);
        return square;
    }

//    @PostMapping(value = "/clear/cache/{cache_name}")
//    public ResponseEntity<Void> clearCacheByName(@PathVariable(name="cache_name") String cacheName){
//        cacheManager.clearCache(cacheName);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping(value="/clear/cache/all")
//    public ResponseEntity<Void> clearCacheAll(){
//        cacheManager.clearCacheAll();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}

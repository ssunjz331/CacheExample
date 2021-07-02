package com.example.redis.controller;

import com.example.redis.dto.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@RestController
public class RedisController {

    /**
     * @Cacheble은 캐시가 있으면 캐시의 정보를 가져오고, 없으면 등록한다.
     *
     * @Cacheble은 캐시가 있으면 캐시의 정보를 가져오고, 없으면 등록한다.
     *
     * @CacheEvict -> 캐시삭제
     */

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    CacheManager cacheManager;

    @Cacheable(cacheNames = "memberCache", key = "#name")
    @GetMapping(value = "/addCache")
    public Member addKey(@RequestParam(value = "name") String name,
                                @RequestParam(value = "id") String id){
        logger.info("--------------addKey----------------");

        evictAllCacheValues("memberCache");

        Member member = new Member();
        try{
            member.setName(name);
            member.setId(id);

            } catch(Exception e){
            e.printStackTrace();
        }

        return member;
    }

    @PostMapping("/flushall")
    @CacheEvict(value="cachename")
    public void flush(@RequestParam(value = "cachename") String cachename){
        cacheManager.getCache(cachename).clear();
        logger.info("flushed");
    }


    //cacheManager
    @CacheEvict(cacheNames = {"cacheName"}, allEntries = true)
    public void evictSingleCacheValue(String cachename, String cacheKey){
        cacheManager.getCache(cachename).evict(cacheKey);
    }
    @CacheEvict(cacheNames = {"cacheName"}, allEntries = true)
    public void evictAllCacheValues(String cacheName){
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        cacheManager.getCache(cacheName).clear();
        System.out.println(vop.get("one"));
    }


    @GetMapping(value = "/redisTest")
    public ResponseEntity addRedisKey(){
        logger.info("--------------addRedisKey----------------");
        try{
            ValueOperations<String, Object> vop = redisTemplate.opsForValue();
            vop.set("one","1");
            vop.set("two","2");
            vop.set("three","3");
            System.out.println(vop.get("one"));
        } catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping(value = "/redis/{number}")
    public ResponseEntity getRedisKey(@PathVariable String number){
        logger.info("--------------getRedisKey----------------");
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        String value = (String) vop.get(number);
        logger.info("--------------getRedisKey END----------------");
        return new ResponseEntity(value, HttpStatus.OK);
    }

}

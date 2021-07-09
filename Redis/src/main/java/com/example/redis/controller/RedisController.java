package com.example.redis.controller;

import com.example.redis.constants.RedisConstants;
import com.example.redis.dto.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.metal.MetalMenuBarUI;
import java.lang.reflect.Method;

@RestController
public class RedisController {

    /**
     * @Cacheble은 캐시가 있으면 캐시의 정보를 가져오고, 없으면 등록한다. 캐시가 존재하면 메서드를 실행하지 않고 캐시된 값이 반환됩니다. 캐시가 존재하지 않으면 메서드가 실행되고 리턴되는 데이터가 캐시에 저장
     *
     * @CachePut 캐시에 데이터를 넣거나 수정시 사용합니다. 메서드의 리턴값이 캐시에 없으면 저장하고 있을경우 갱신합니다.
     *
     * @CacheEvict -> 캐시삭제
     *
     * @Caching 여러개의 캐시 annotation을 실행되어야 할때 사용
     */

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    CacheManager cacheManager;

    @Cacheable(cacheNames = RedisConstants.MEMBER_CACHE, key = "#name")
    @GetMapping(value = "/addCache")
    public Member addKey(@RequestParam(value = "name") String name,
                                @RequestParam(value = "id") String id){
        logger.info("--------------addKey----------------");

        evictAllCacheValues(RedisConstants.MEMBER_CACHE);

        Member member = new Member();
        try{
            member.setName(name);
            member.setId(id);

            } catch(Exception e){
            e.printStackTrace();
        }

        return member;
    }

    @CachePut(value = RedisConstants.MEMBER_CACHE, key = "#name")
    @GetMapping(value = "/updateCache")
    public Member update(@RequestParam(value = "name") String name,
                         @RequestParam(value = "id") String id){
        Member member = (Member)cacheManager.getCache(RedisConstants.MEMBER_CACHE).get(name);
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

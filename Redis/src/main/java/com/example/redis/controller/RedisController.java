package com.example.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = "/redisTest")
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

    @GetMapping(value = "/redis/{key}")
    public ResponseEntity getRedisKey(@PathVariable String key, HttpServletRequest httpServletRequest){
        logger.info("--------------getRedisKey----------------");
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        String value = (String) vop.get(key);
        logger.info("--------------getRedisKey END----------------");
        return new ResponseEntity(value, HttpStatus.OK);
    }

}

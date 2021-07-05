package com.example.ehcache3.controller;

//import com.example.ehcache3.util.EhCacheManager;
import com.example.ehcache3.model.Employee;
//import com.example.ehcache3.model.Square;
//import com.example.ehcache3.model.Task;
import com.example.ehcache3.service.EhCacheService;
import com.example.ehcache3.util.EhCacheManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * https://medium.com/finda-tech/spring-%EB%A1%9C%EC%BB%AC-%EC%BA%90%EC%8B%9C-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-ehcache-4b5cba8697e0
 * https://www.javadevjournal.com/spring-boot/spring-boot-with-ehcache/
 */
@RestController
public class EhCacheController {

    private static final Logger logger = LoggerFactory.getLogger(EhCacheController.class);

    @Autowired
    EhCacheService service;

    @Autowired
    EhCacheManagerUtil util;

    @Autowired
    CacheManager cacheManager;

//    @Cacheable(value = "squareCache", key = "#number", condition = "#number > 10")
//    @GetMapping(value = "/ehcahe/{number}")
//    public String getSquare(@PathVariable Long number){
//        logger.info("====== EhCacheController - getSquare =====");
//        Cache cache = cacheManager.getCache("squareCache");
//
//        Square squareDto = new Square();
//        try{
//
//            squareDto.setNumber(number);
//            squareDto.setSquarevalue(service.square(number));
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//
//        System.out.println("squareDto = "+squareDto.toString());
//        System.out.println("cache = " + cache.get(3L));
//
//        return String.format("{\"square\":%s}", service.square(number));
//    }

//    @GetMapping(value = "employee")
//    public void employee(){
//        Cache cache = cacheManager.getCache("employeeCache");
//
//        System.out.println(service.getEmployeeById(1L));
//
//        //This will hit the cache - verify the message in console output
//        System.out.println(service.getEmployeeById(2L));
//
//        //Add entry to cache
//        cache.put(3L, new Employee(3L, "Charles", "Dave"));
//
//        //Get entry from cache
//        System.out.println(cache.get(3L).get());
//
//    }

    @Cacheable(cacheNames = "employeeCache", key = "#id")
    @GetMapping(value = "/add/employee")
    public Employee addEmployee(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "firstName") String firstName,
                               @RequestParam(value = "lastName") String lastName){
        service.addEmployee(id,firstName,lastName);

        Cache cache = cacheManager.getCache("employeeCache");
        service.getEmployeeById(id);
        return service.addEmployee(id,firstName,lastName);
    }

    @GetMapping(value = "/get/employee/{id}")
    public Employee getEmployee(@PathVariable Long id){
        Cache cache = cacheManager.getCache("employeeCache");
        try {
            cache.get(id).get();
        }catch (NullPointerException e){
            e.printStackTrace();
            logger.error("해당 cacheName의 cache는 Null.");
        }
        return (Employee) cache.get(id).get();
    }

//    @GetMapping(path = "/all")
//    public List<Task> getAllTasks() {
//        logger.info("call taskService.findAll");
//        return service.findAll();
//    }

//    @GetMapping(path = "/all/{userId}")
//    public List<Task> getAllTasksByUserId(@PathVariable Integer userId) {
//        logger.info("call taskService.findAllByUserId");
//        return service.findAllByUserId(userId);
//    }

    @PostMapping(value = "/clear/cache/{cache_name}")
    public ResponseEntity<Void> clearCacheByName(@PathVariable(name = "cache_name") String cacheName) {
        util.clearCache(cacheName);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "/clear/cache/all")
    public ResponseEntity<Void> clearCacheAll() {
        util.clearCacheAll();
        return new ResponseEntity<Void>(HttpStatus.OK);
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

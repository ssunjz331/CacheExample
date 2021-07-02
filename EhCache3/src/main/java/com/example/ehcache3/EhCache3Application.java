package com.example.ehcache3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EhCache3Application {

    public static void main(String[] args) {
        SpringApplication.run(EhCache3Application.class, args);
    }

}

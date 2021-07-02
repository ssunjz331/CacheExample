package com.example.ehcache3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EhCacheController {

    @GetMapping(value = "/ehcahe/{number}")
    public String getSquare(@PathVariable String number){
        return number;
    }

}

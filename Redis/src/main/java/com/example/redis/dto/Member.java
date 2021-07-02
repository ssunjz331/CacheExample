package com.example.redis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private String id;
    private String name;

    public Member(String id, String name) {
    }

    public Member() {

    }
}

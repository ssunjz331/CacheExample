package com.example.redis.dto;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("member")
@Data
public class Member implements Serializable {
    private String id;
    private String name;

    public Member(String id, String name) {
    }

    public Member() {

    }
}

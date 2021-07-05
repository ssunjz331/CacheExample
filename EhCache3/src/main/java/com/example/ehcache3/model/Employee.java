package com.example.ehcache3.model;

import lombok.Data;

import java.io.Serializable;


/**
 * https://howtodoinjava.com/spring-boot2/ehcache3-config-example/
 */
@Data
public class Employee implements Serializable
{
    private static final long serialVersionUID = 5517244812959569947L;

    private Long id;
    private String firstName;
    private String lastName;

    public Employee() {
        super();
    }

    public Employee(Long id, String firstName, String lastName) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
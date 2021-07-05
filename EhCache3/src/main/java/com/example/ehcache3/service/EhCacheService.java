package com.example.ehcache3.service;

import com.example.ehcache3.controller.EhCacheController;
import com.example.ehcache3.model.Employee;
import com.example.ehcache3.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EhCacheService {

    private static final Logger logger = LoggerFactory.getLogger(EhCacheService.class);

    @Cacheable(value = "squareCache", key = "#number", condition = "#number > 10")
    public BigDecimal square(Long number){
        BigDecimal square = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(number));
        logger.info("square of {} is {}", number, square);
        return square;
    }


    @Cacheable(value = "taskCache")
    public List<Task> findAll(){
        logger.info("findAll tashCache");
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "My First task", true));
        tasks.add(new Task(2L, "My Second task", false));
        return tasks;
    }

    @Cacheable(value = "taskByUserIdCache", key = "#userId")
    public List<Task> findAllByUserId(Integer userId){
        logger.info("findAll By UserId");
        List<Task> tasks = new ArrayList<>();

        switch (userId) {
            case 1:
                tasks.add(new Task(1L, "1's First task", true));
                tasks.add(new Task(2L, "1's Second task", false));
                break;
            case 2:
                tasks.add(new Task(4L, "2's First task", true));
                break;
            case 3:
                tasks.add(new Task(3L, "3's First task", true));
                tasks.add(new Task(5L, "3's Second task", true));
                tasks.add(new Task(6L, "3's Third task", false));
                break;
            default:
                break;
        }

        return tasks;
    }


    @Cacheable(cacheNames="employeeCache", key="#id")
    public Employee getEmployeeById(Long id) {
        HashMap<Long, Employee> db = new HashMap<>();
//        db.put(1L, new Employee(1L, "Alex", "Gussin"));
//        db.put(2L, new Employee(2L, "Brian", "Schultz"));

//        System.out.println("Getting employee from DB");

        return db.get(id);
    }

    @Cacheable(cacheNames = "employeeCache", key = "#id")
    public Employee addEmployee(Long id, String firstName, String lastName){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return employee;
    }


}

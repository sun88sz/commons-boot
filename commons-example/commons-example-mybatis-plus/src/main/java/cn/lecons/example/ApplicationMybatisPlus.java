package com.sun.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : Sun
 * @date : 2018/7/16 17:05
 */
@SpringBootApplication
public class ApplicationMybatisPlus {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ApplicationMybatisPlus.class, args);
    }
}

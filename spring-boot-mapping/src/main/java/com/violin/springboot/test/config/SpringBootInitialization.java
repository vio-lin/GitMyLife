package com.violin.springboot.test.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.violin.springboot.test")
public class SpringBootInitialization {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitialization.class);
    }
}

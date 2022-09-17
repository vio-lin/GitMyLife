package com.violin.springboot.test;

import com.violin.springboot.test.config.SpringBootInitialization;
import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringBootInitialization.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

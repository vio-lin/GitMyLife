package com.violin.test.sync;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author lin
 * Date: 2019-03-31
 */
public class GuavaRateLimiter {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1,30,TimeUnit.SECONDS);
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                rateLimiter.acquire();
                System.out.println("开始执行工作"+ new Date());
            }).start();
        }
    }
}

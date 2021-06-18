package com.violin.busniss.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnThreadSafeConcurrentHashMapEx2 {
    private static final int maxThreadCount = 10;
    private static final int maxItemCount = 1000;

    public static void main(String[] args) {
        // ConcurrentHashMap 只能保证提供的`原子性读写操作`是线程安全的。
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);
        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap();
        for (int i = 0; i < maxThreadCount; i++) {
            final int threadNo = i;
            executorService.submit(() -> {
                try {
                    while (hashMap.size() < maxItemCount) {
                        hashMap.put(Long.toString(System.currentTimeMillis()), Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getName() + ":" + hashMap.size());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(hashMap.size());
    }
}

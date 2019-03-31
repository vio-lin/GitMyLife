package com.violin.test.sync;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lin
 * Date: 2019-03-31
 */
public class SemaphoreTest {
    static AtomicInteger currentCount = new AtomicInteger(0);
    public static void main(String[] args) {
        int semaphCount = 10;
        //银行办卡 值允许10个人在银行大厅
        Semaphore semaphore = new Semaphore(semaphCount);

        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(()->{
                try {
                    // 拿到进入的许可
                    semaphore.acquire();
                    System.out.println("存在空位:"+semaphore.availablePermits());
                    doSomething(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }).start();
        }
    }

    private static void doSomething(int i) {
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("接待了"+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

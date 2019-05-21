package com.violin.test.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lin
 * Date: 2019-03-31
 */
public class CountDownLatchTest {
    public static void main(String[] args){
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(()->{
                while(true){
                    //模拟加载缓存之类的操作
                    doSomePrepare();
                    latch.countDown();
                    System.out.println("剩下未初始化资源"+latch.getCount());
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    doRealJob(finalI);
                }
            }).start();
            // 10*1000秒后疯狂跑
        }
    }

    private static void doRealJob(int finalI) {
        System.out.println(finalI +"号线程在跑动");
    }

    /**
     * 提前做一些操作的准备
     */
    private static void doSomePrepare(){
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

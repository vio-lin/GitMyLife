package com.violin.test.sync;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lin
 * Date: 2019-03-31
 */
public class CyclicBarrierTest {
    static AtomicInteger driverCount = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        // 设置人力车 车上有个10个人力加速器 只有全到才给发车
        // 由于只有一个车道只有前面的车满了后面的车才可以进入
        int partiesCount = 10;
        CyclicBarrier barrier = new CyclicBarrier(partiesCount);

        for (int i = 0; i < 30 ; i++) {
            int finalI = i;
            new Thread(()->{
                doSomePrepare();
                System.out.println("已经存在司机"+barrier.getNumberWaiting());
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                driverCount.addAndGet(0);
                doSomeWork(finalI);
            }).start();
            // 每个300ms 来一个司机
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }

    private static void doSomeWork(int finalI) {
        System.out.println(finalI +"号线程在处理");
    }

    private static void doSomePrepare() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

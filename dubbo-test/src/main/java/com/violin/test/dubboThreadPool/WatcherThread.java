package com.violin.test.dubboThreadPool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lin
 * Date: 2019-03-10
 */
public class WatcherThread implements Runnable{
    private ThreadPoolExecutor executor;

    public WatcherThread(ThreadPoolExecutor executor) {
        this.executor = executor;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(executor.toString());
        }
    }
}

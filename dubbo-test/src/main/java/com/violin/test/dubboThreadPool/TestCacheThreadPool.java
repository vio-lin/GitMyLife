package com.violin.test.dubboThreadPool;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadlocal.NamedInternalThreadFactory;
import org.apache.dubbo.common.threadpool.support.AbortPolicyWithReport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lin
 */
public class TestCacheThreadPool extends AbstractTest{
    public static void main(String[] args) throws InterruptedException {
        String name = "cacheThread";
        int cores = 10;
        int threads = 15;
        int alive = 5000;
        int queues = 0;
        URL url = mockUrl();
        ThreadPoolExecutor executor =  new ThreadPoolExecutor(cores, threads, alive, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                                : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedInternalThreadFactory(name, true), new AbortPolicyWithReport(name, url));

        new Thread(new WatcherThread(executor)).start();

        int  workTime = 2000;
        for(int i =1;i<15;i++){
            Thread.sleep(80);
            executor.submit(new WorkThread(workTime));
            System.out.println("现在是第"+i+"个线程");
        }



    }
}

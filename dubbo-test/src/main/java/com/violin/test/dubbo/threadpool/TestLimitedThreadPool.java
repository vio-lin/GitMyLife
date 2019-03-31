package com.violin.test.dubbo.threadpool;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadlocal.NamedInternalThreadFactory;
import org.apache.dubbo.common.threadpool.support.AbortPolicyWithReport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lin
 * Date: 2019-03-10
 */
public class TestLimitedThreadPool extends AbstractThreadTest {
    public static void main(String[] args) throws InterruptedException {
        int cores = 10;
        int threads = 15;
        int queues = 5000;
        String name = "LimiteThreadPool";
        URL url = mockUrl();
        //下面的代码 来自dubbo 源码
        @SuppressWarnings("all")
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cores, threads, Long.MAX_VALUE, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                                : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedInternalThreadFactory(name, true), new AbortPolicyWithReport(name, url));
        new Thread(new WatcherThread(executor)).start();

        int workTime = 50000;
        for (int i = 1; i < 100; i++) {
            Thread.sleep(100);
            executor.submit(new WorkThread(workTime));
        }
    }
}

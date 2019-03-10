package com.violin.test.dubboThreadPool;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadlocal.NamedInternalThreadFactory;
import org.apache.dubbo.common.threadpool.support.AbortPolicyWithReport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestFixThreadPool extends AbstractTest {
    public static void main(String[] args) throws InterruptedException {
        int threads = 3;
        //三个分支这么厉害的吗
        int queues = 20;
        String name = "FixNameFactory";
        URL url = mockUrl();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                                : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedInternalThreadFactory(name, true), new AbortPolicyWithReport(name, url));
        int workTime = 10000;

        new Thread(new WatcherThread(executor)).start();

        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            executor.submit(new WorkThread(workTime));
        }
    }
}

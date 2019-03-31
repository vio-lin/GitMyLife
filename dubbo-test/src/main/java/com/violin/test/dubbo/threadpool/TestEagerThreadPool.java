package com.violin.test.dubbo.threadpool;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadlocal.NamedInternalThreadFactory;
import org.apache.dubbo.common.threadpool.support.AbortPolicyWithReport;
import org.apache.dubbo.common.threadpool.support.eager.EagerThreadPoolExecutor;
import org.apache.dubbo.common.threadpool.support.eager.TaskQueue;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author lin
 * Date: 2019-03-10
 */
public class TestEagerThreadPool extends AbstractThreadTest {
    public static void main(String[] args) throws InterruptedException {
        int queues = 5;
        int cores = 10;
        int threads = 15;
        int alive = 5000;
        String name = "Eage Thread Pool";
        URL url =  mockUrl();
        //下面的代码 来自dubbo 源码
        @SuppressWarnings("all")
        TaskQueue<Runnable> taskQueue = new TaskQueue<Runnable>(queues <= 0 ? 1 : queues);
        EagerThreadPoolExecutor executor = new EagerThreadPoolExecutor(cores,
                threads,
                alive,
                TimeUnit.MILLISECONDS,
                taskQueue,
                new NamedInternalThreadFactory(name, true),
                new AbortPolicyWithReport(name, url));
        taskQueue.setExecutor(executor);

        new Thread(new WatcherThread(executor)).start();
        int workTime = 50000;
        for(int i=1;i<100;i++){
            Thread.sleep(100);
            executor.submit(new WorkThread(workTime));
        }
    }
}

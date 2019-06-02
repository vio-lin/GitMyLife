package com.violin.test.java8.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author guo.lin  2019/5/30
 */
public class DownloadTask implements Task{
    long TIME_TO_FINISHTASK = 1000;
    @Override
    public String doSomeThing() throws InterruptedException {
        Thread.sleep(TIME_TO_FINISHTASK);
        return "收到了";
    }

    @Override
    public CompletableFuture<String> doSomeThingAsync() {
        return createMethod2();

    }

    private CompletableFuture<String> createMethod1() {
        CompletableFuture<String> future = new CompletableFuture<>();
        ExecutorService threadPool = new ThreadPoolExecutor(1,1 ,10 ,TimeUnit.MICROSECONDS ,new LinkedBlockingQueue<Runnable>() );
        threadPool.submit(()->{
            try{
                future.complete(doSomeThing());
            }catch (Exception e) {
                future.completeExceptionally(e);
            }

        });
        return future;
    }

    private CompletableFuture<String> createMethod2() {
        /**
         * supplyAsync方法接受一个生产者（Supplier）作为参数，返回一个CompletableFuture
         * 对象，该对象完成异步执行后会读取调用生产者方法的返回值。生产者方法会交由ForkJoinPool
         * 池中的某个执行线程（Executor）运行，但是你也可以使用supplyAsync方法的重载版本，传
         * 递第二个参数指定不同的执行线程执行生产者方法。
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> {
            try {
                return doSomeThing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        return future;
    }

}

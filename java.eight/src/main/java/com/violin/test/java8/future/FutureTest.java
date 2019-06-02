package com.violin.test.java8.future;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author guo.lin  2019/5/30
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> shopList = Arrays.asList(new String[]{"shop1","shop2","shop3"});
//        doSync(shopList);
        doAsync(shopList);
    }

    private static void doSync(List<String> shopList) {
        DownloadTask task = new DownloadTask();
        long startTime = System.currentTimeMillis();
        // 1.改成 stream-> parallelStream 很关键
        List<String> resultList = shopList.parallelStream().map(a->{
            try {
                return task.doSomeThing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  null;
        }).collect(Collectors.toList());
        System.out.println(System.currentTimeMillis()-startTime);
        sleep();
        System.out.println((System.currentTimeMillis()-startTime)+":"+ String.join("|",resultList));
    }

    private static void doAsync(List<String> shopList) {
        DownloadTask task = new DownloadTask();
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<String>> resultFutureList = shopList.stream().map(a->{ return task.doSomeThingAsync();}).collect(Collectors.toList());
        System.out.println(System.currentTimeMillis()-startTime);
        sleep();
        List<String> value = null;
        value = resultFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println((System.currentTimeMillis()-startTime)+":"+String.join("|",value));
    }

    public static void sleep(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

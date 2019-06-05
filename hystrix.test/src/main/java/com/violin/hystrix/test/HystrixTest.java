package com.violin.hystrix.test;

/**
 * @author guo.lin  2019/6/4
 */
public class HystrixTest {
    public static void main(String[] args) {
        QueryTask task = new QueryTask();
        for (int i = 0; i < 100; i++) {
            new MyHystrixCommand(task).execute();
        }
    }
}

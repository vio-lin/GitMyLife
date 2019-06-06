package com.violin.hystrix.test.command;

/**
 * @author guo.lin  2019/6/4
 */
public class HystrixCommandTest {
    public static void main(String[] args) {
        QueryTask task = new QueryTask();
        for (int i = 0; i < 100; i++) {
            new MyHystrixCommand(task).execute();
        }
    }
}

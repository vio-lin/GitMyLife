package com.violin.test.dubbo.threadpool;

import java.util.concurrent.TimeUnit;

public class WorkThread implements Runnable{
    private long sleepime;
    WorkThread(long sleep){
        this.sleepime = sleep;
    }
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

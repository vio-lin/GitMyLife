package com.violin.practise.currency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new CountStartTask(0, 300));
        new CountDownLatch(1).await();

    }

    static class CountStartTask extends RecursiveAction {
        private int start;
        private int end;

        public CountStartTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= 6) {
                System.out.println("数到了" + start + "-" + end);
                for (int i = start; i < end; i++) {
                    System.out.println("当前数到了第" + i + "颗星星 at " + Thread.currentThread().getId());
                }
            } else {
                int middle = (start + end) / 2;
                CountStartTask task1 = new CountStartTask(start, middle);
                CountStartTask task2 = new CountStartTask(middle, end);
                task1.fork();
                task2.fork();
                task1.join();
                task2.join();
            }
        }
    }
}

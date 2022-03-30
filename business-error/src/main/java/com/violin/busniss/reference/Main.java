package com.violin.busniss.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ReferenceQueue<Integer> referenceQueue = new ReferenceQueue<Integer>();
//    WeakReference<Integer> integer = new WeakReference<Integer>(new Integer(23),referenceQueue);
    // 因为 -127 128 之间的数字是会被在内存中被缓存的所以不会被回收
    WeakReference<Integer> integer = new WeakReference<Integer>(256,referenceQueue);
    System.gc();
    Thread thread = new Thread(() -> {
      try {
        int cnt = 0;
        WeakReference<byte[]> k;
        while((k = (WeakReference) referenceQueue.remove()) != null) {
          System.out.println((cnt++) + "回收了:" + k);
        }
      } catch(InterruptedException e) {
        //结束循环
      }
    });
    thread.setDaemon(true);
    thread.start();
    new CountDownLatch(1).await();
  }
}

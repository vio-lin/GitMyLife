package com.violin.busniss.theadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 创建一个可以可以提前扩展core的队列 到了最大之后再 把所有的任务 往queue里面扔
 * 需要做两件事情: 1. 设置当前queue 返回是满的状态 2. 在丢弃任务的逻辑中 重新将任务放到queue中 假如放不进器 在抛一遍错误
 *
 * @author guo.lin 2020/10/12
 */
public class EagerThreadPool {
  private static final Logger log = LoggerFactory.getLogger(EagerThreadPool.class);

  public static void main(String[] args) throws InterruptedException {
    EagerThreadPool instance = new EagerThreadPool();
    instance.better();
  }

  public int better() throws InterruptedException {
    //这里开始是激进线程池的实现
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10) {
      @Override
      public boolean offer(Runnable e) {
        //先返回false，造成队列满的假象，让线程池优先扩容
        return false;
      }
    };

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 5,
            5, TimeUnit.SECONDS,
            queue, new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(), (r, executor) -> {
      try {
        //等出现拒绝后再加入队列
        //如果希望队列满了阻塞线程而不是抛出异常，那么可以注释掉下面三行代码，修改为executor.getQueue().put(r);
        if (!executor.getQueue().offer(r, 0, TimeUnit.SECONDS)) {
          throw new RejectedExecutionException("ThreadPool queue full, failed to offer " + r.toString());
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
    //激进线程池实现结束

    printStats(threadPool);
    //每秒提交一个任务，每个任务耗时10秒执行完成，一共提交20个任务

    //任务编号计数器
    AtomicInteger atomicInteger = new AtomicInteger();

    IntStream.rangeClosed(1, 20).forEach(i -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      int id = atomicInteger.incrementAndGet();
      try {
        threadPool.submit(() -> {
          log.info("{} started", id);
          try {
            TimeUnit.SECONDS.sleep(10);
          } catch (InterruptedException e) {
          }
          log.info("{} finished", id);
        });
      } catch (Exception ex) {
        log.error("error submitting task {}", id, ex);
        atomicInteger.decrementAndGet();
      }
    });

    TimeUnit.SECONDS.sleep(60);
    return atomicInteger.intValue();
  }

  private void printStats(ThreadPoolExecutor threadPool) {
    System.out.println(threadPool);
  }
}

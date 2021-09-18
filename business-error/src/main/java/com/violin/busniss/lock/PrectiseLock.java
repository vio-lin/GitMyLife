package com.violin.busniss.lock;

/**
 * @author guo.lin
 * 这个类用来证明锁的粒度是县城而非操作或者调用
 */
public class PrectiseLock {
  public static void main(String[] args) {
    System.out.println(System.currentTimeMillis());
    new SonClass().doSomeThing();
    System.out.println("fin" + System.currentTimeMillis());
  }

  static class FatherClass {
    synchronized void doSomeThing() {

    }
  }

  static class SonClass extends FatherClass {
    @Override
    synchronized void doSomeThing() {
      super.doSomeThing();
    }
  }
}

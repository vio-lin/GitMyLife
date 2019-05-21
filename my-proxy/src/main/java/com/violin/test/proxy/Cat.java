package com.violin.test.proxy;

/**
 * @author guo.lin  2019/4/2
 */
@SuppressWarnings("all")
public class Cat implements Animal {

  @Override
  public void eat(){
    System.out.println("cat eat milk");
  }
}

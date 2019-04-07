package com.violin.test.proxy;

import java.lang.reflect.Method;

/**
 * @author guo.lin  2019/4/2
 */
public class MyInvocation implements MyInvocationHandler {
  private Animal animal;

  public MyInvocation(Animal animal) {
    this.animal = animal;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
    before();
    Object invoke = method.invoke(animal,null);
    after();
    return invoke;
  }

  private void after() {
    System.out.println("在方法执行的位置之后");
  }

  private void before() {
    System.out.println("在方法执行的位置之前");
  }
}

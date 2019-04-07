package com.violin.test.proxy;

/**
 * @author guo.lin  2019/4/2
 */
public class TestClass {
  public static void main(String[] args) throws Throwable {
    Animal cat = new Cat();
    MyInvocationHandler myInvocationHandler = new MyInvocation(cat);
    Animal proxyAnimal = (Animal) MyProxy.newProxyInstance(new MyClassLoader("D:\\git\\GitMyLife\\my-proxy\\src\\main\\java\\com\\violin\\test\\proxy\\","com.violin.test.proxy" ),Animal.class ,myInvocationHandler);
    System.out.println(proxyAnimal.getClass().getName());
    proxyAnimal.eat();
  }
}

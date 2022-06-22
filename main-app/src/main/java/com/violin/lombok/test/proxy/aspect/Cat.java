package com.violin.lombok.test.proxy.aspect;

public class Cat implements Animal {
  @Override
  public void eat(String food) {
    System.out.println("eat some thing : " + food);
  }
}

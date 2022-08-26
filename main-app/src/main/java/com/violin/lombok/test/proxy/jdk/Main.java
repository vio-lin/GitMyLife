package com.violin.lombok.test.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
  public static void main(String[] args) {
    Cat cat = new Cat();
    Animal animal = (Animal)Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{Animal.class}, new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("eat".equals(method.getName())){
                  System.out.println("print some code");
                  return method.invoke(cat,args);
                }

                return method.invoke(cat,args);
              }
            }
    );

    animal.eat("apple");
  }

}

package com.violin.lombok.test.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.security.CodeSource;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(Cat.class);
    enhancer.setInterfaces(new Class[]{Animal.class});
    Cat instance = new Cat();
    enhancer.setCallback(new MethodInterceptor() {
      @Override
      public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if("eat".equals(method.getName())){
          System.out.println("在当前方法之前");
          return method.invoke(instance,objects);
        }
        return method.invoke(instance,objects);
      }
    });
    Animal animal = (Animal) enhancer.create();
    animal.eat("someFood");
  }
}

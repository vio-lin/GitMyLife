package com.violin.lombok.test.proxy.aspect;//package com.violin.lombok.test.proxy.bytebuddy;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;


public class Main {
  public static void main(String[] args) {
    AspectJProxyFactory factory = new AspectJProxyFactory();
    factory.setTargetClass(Cat.class);
    factory.setInterfaces(new Class[]{Animal.class});
    factory.setProxyTargetClass(true);
    Cat instance = new Cat();
    factory.addAdvice((MethodInterceptor) invocation ->{
          if("eat".equals(invocation.getMethod().getName())){
            System.out.println("在方法执行之前");
            return invocation.getMethod().invoke(instance,invocation.getArguments());
          }
          return invocation.getMethod().invoke(instance,invocation.getArguments());
     });
    //factory.addAspect(AspectConfig.class);
    Animal animal = factory.getProxy();
    animal.eat("someFood");
  }

}

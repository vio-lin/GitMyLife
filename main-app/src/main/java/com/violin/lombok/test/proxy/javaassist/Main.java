package com.violin.lombok.test.proxy.javaassist;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.security.CodeSource;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
    String classDirectory = codeSource.getLocation().getPath();
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.writeDirectory = classDirectory;
    proxyFactory.setSuperclass(Cat.class);
    proxyFactory.setInterfaces(new Class[]{Animal.class});
    Class<Cat> proxyFactoryClass = (Class<Cat>) proxyFactory.createClass();
    Cat instance = new Cat();
    Cat cat = proxyFactoryClass.newInstance();
    ((Proxy)cat).setHandler(new MethodHandler(){
      @Override
      public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        if("eat".equals(thisMethod.getName())){
          System.out.println("在方法前面");
          return thisMethod.invoke(instance,args);
        }
        return thisMethod.invoke(instance,args);
      }
    });
    cat.eat("some food");
  }
}

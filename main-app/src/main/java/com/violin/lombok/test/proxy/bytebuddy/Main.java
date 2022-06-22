//package com.violin.lombok.test.proxy.bytebuddy;
//
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.dynamic.DynamicType;
//import net.bytebuddy.implementation.MethodDelegation;
//import net.bytebuddy.matcher.ElementMatchers;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.security.CodeSource;
//import java.util.concurrent.Callable;
//
//import org.xml.sax.SAXException;
//
//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter;
//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
//
//
//public class Main {
//  public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
//    CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
//    Cat instance = new Cat();
//    DynamicType.Loaded<?> dynamicType = new ByteBuddy()
//            .subclass(Cat.class)
//            .implement(new Class[]{Animal.class})
//            .method(ElementMatchers.any())
//            .intercept(MethodDelegation.to(new Intercepter() {
//              @Override
//              public Object intercept(UnmarshallingContext.State state, Object o) throws SAXException {
//
//                return null;
//              }
//            })).make()
//            .load(Main.class.getClassLoader();
//    File file = new File(codeSource.getLocation().getPath());
//    file.setReadable(true, false);
//    dynamicType.saveIn(file);
//    Class<?> orixyClass = dynamicType.getLoaded();
//    animal.eat("someFood");
//  }
//
//  public static class Intercept{
//    private Cat instance;
//    public Intercept(Cat cat){
//      this.instance = cat;
//    }
//
//    public Object intercept(Object thisObject, Object[] argument, Callable<>)
//  }
//
//}

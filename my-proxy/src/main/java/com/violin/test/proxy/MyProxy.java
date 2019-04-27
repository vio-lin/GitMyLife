package com.violin.test.proxy;

import org.springframework.util.FileCopyUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author guo.lin 2019/4/2
 */
public class MyProxy {
  private static final String RT = "\r";

  public static Object newProxyInstance(MyClassLoader loader,Class<?> interfaces,MyInvocationHandler handler)throws IllegalArgumentException{
    if( handler == null){
      throw new NullPointerException();
    }

    //根据接口构造代理类： $MyProxy0
    Method[] methods = interfaces.getMethods();
    StringBuilder proxyClassString = new StringBuilder();
    proxyClassString.append("package ")
            .append(loader.getProxyClassPackage()).append(";").append(RT)
            .append("import java.lang.reflect.Method;").append(RT)
            .append("public class $MyProxy0 implements ").append(interfaces.getName()).append("{").append(RT)
            .append("MyInvocationHandler h;").append(RT)
            .append("public $MyProxy0(MyInvocationHandler h){").append(RT).append("this.h = h;}").append(RT)
            .append(getMethodString(methods,interfaces)).append("}");
    //写入java文件 进行编译
    String fileName = loader.getDir()+ File.separator+"$MyProxy0.java";
    File myProxyFile = new File(fileName);
    try{
      compile(proxyClassString,myProxyFile);
      Class $myProxy0 = loader.findClass("$MyProxy0");
      //SMyProxy初始化
      Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);
      return constructor.newInstance(handler);
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  private static String getMethodString(Method[] methods, Class interfaces){
    StringBuilder methodStringBuffer = new StringBuilder();
    for(Method method : methods){
      methodStringBuffer.append("public void ").append(method.getName())
              .append("()").append("throws Throwable{ ")
              .append("Method method1 = ").append(interfaces.getName())
              .append(".class.getMethod(\"").append(method.getName())
              .append("\", new Class[]{});")
              .append("this.h.invoke(this,method1,null);}").append(RT);
    }
    return methodStringBuffer.toString();
  }

  private static void compile(StringBuilder proxyClassString,File myProxyFile) throws IOException {
    FileCopyUtils.copy(proxyClassString.toString().getBytes(),myProxyFile);
    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null,null );
    Iterable javaFileObjects = standardFileManager.getJavaFileObjects(myProxyFile);
    CompilationTask task = javaCompiler.getTask(null, standardFileManager ,null ,null ,null , javaFileObjects);
    task.call();
    standardFileManager.close();
  }

}

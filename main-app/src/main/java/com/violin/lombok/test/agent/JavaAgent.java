package com.violin.lombok.test.agent;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class JavaAgent {

  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.println("Agent args is : "  + agentArgs);
    inst.addTransformer(new MyTransformer());
  }

  static class MyTransformer implements ClassFileTransformer {
    private String injectedClass= "com.violin.test.TimeTask";
    private final String injectedMethod = "test";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
      String realClassName = className.replace("/", ".");

      if (realClassName.equals(injectedClass)) {
        CtClass ctClass;
        try {
          // 使用全称，取得字节码类<使用javassist>
          ClassPool classPool = ClassPool.getDefault();
          ctClass = classPool.get(realClassName);

          // 得到方法实例
          CtMethod ctMethod = ctClass.getDeclaredMethod(injectedMethod);
          // 添加变量
          ctMethod.addLocalVariable("time", CtClass.longType);
          ctMethod.insertBefore("System.out.println(\"------------ Before --------\");");
          ctMethod.insertBefore("time = System.currentTimeMillis();");

          ctMethod.insertAfter("System.out.println(\"Elapsed Time(ms): \" + (System.currentTimeMillis() - time));");
          ctMethod.insertAfter("System.out.println(\"------------- After --------\");");

          return ctClass.toBytecode();
        } catch (Throwable e) { //这里要用Throwable，不要用Exception
          System.out.println(e.getMessage());
          e.printStackTrace();

        }
      }

      // 返回原类字节码
      return classfileBuffer;
    }
  }

}

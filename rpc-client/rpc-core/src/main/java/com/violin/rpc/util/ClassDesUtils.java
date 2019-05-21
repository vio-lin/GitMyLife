package com.violin.rpc.util;

/**
 * @author guo.lin  2019/4/17
 */
public class ClassDesUtils {
  private ClassDesUtils(){};

  //TODO 考虑没有参数 或者 Invocation里面直接存储的是Class<?>[]
  public static String[] stringToClassArray(String parameterString) {
    return parameterString.split(",");
  }

  // TODO 考虑多个参数的情况
  public static String getParameterType(Class<?>[] parameterType){
    return parameterType[0].getName();
  }
}

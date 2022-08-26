package com.violin.grpc.demo.custom;

public class CodeContext {
  private static ThreadLocal<CodeContext> cache = ThreadLocal.withInitial(()->{
    return new CodeContext();
  });
  private String codeString;

  public String getCodeString() {
    return codeString;
  }

  public void setCodeString(String codeString) {
    this.codeString = codeString;
  }

  public static CodeContext getContext(){
    return cache.get();
  }
}

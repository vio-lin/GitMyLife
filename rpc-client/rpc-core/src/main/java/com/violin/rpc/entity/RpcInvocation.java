package com.violin.rpc.entity;

/**
 * @author guo.lin  2019/4/15
 */
public class RpcInvocation {
  String methodName;
  String className;
  String parameters;
  Object requestType;

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getParameters() {
    return parameters;
  }

  public void setParameters(String parameters) {
    this.parameters = parameters;
  }

  public Object getRequestType() {
    return requestType;
  }

  public void setRequestType(Object requestType) {
    this.requestType = requestType;
  }

  public void setRequestType(Object[] requestType) {
    this.requestType = requestType;
  }
}

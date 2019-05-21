package com.violin.test.proxy;

import java.lang.reflect.Method;

/**
 * @author guo.lin  2019/4/2
 */
@SuppressWarnings("all")
public interface MyInvocationHandler {
  public Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}

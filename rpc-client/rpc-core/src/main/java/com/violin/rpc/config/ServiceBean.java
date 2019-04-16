package com.violin.rpc.config;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author guo.lin  2019/4/15
 */
public class ServiceBean<T> {
  public static Map<Class<?>,Proxy> proxyMap;
  public void export(){

  }
}

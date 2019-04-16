package com.violin.demo.api.server;

import com.violin.demo.api.DemoRequest;
import com.violin.demo.api.DemoResponse;
import com.violin.demo.api.DemoService;
import com.violin.demo.api.service.ServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guo.lin  2019/4/15
 * 本地对于 使用两个代理实现服务的相互调用
 */
public class SingleServerStarter {
  public static Map<Class<?>, Object> proxyMap = new HashMap<>();

  public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
   SingleServerStarter application = new SingleServerStarter();
   application.proxyInServer();
   application.proxyInClient();
  }

  private void proxyInClient() {
    DemoRequest request = new DemoRequest();
    request.setInstant(Instant.now());
    request.setReq("someMessage");

    // invoke 中间还缺了一点 object中间还缺了很重要的东西 className method MetodType
    DemoService client = (DemoService) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class<?>[]{DemoService.class} ,new MyInvocationHandler() );
    DemoResponse response = client.call(request);
    System.out.println(response);
  }

  public void proxyInServer() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    Class<?> interfaceClass = DemoService.class;
    Class<?> serviceImplClass = ServiceImpl.class;
    DemoRequest request = new DemoRequest();
    String methodName = "call";
    Class<?>[] parameters = new Class[]{DemoRequest.class};
    Constructor<ServiceImpl>[] constructors = (Constructor<ServiceImpl>[]) serviceImplClass.getConstructors();
    for (Constructor<ServiceImpl> constructor : constructors) {
      if (constructor.getParameterCount() == 0) {
        ServiceImpl service = constructor.newInstance();
        proxyMap.put(interfaceClass, service);
      }
    }

    // object中间还缺了很重要的东西 className method MetodType
    DemoService service = (DemoService) proxyMap.get(interfaceClass);
    Method method = DemoService.class.getMethod(methodName,parameters);
    DemoResponse response = (DemoResponse) method.invoke(service,request);
    System.out.println(response);
  }

  static class MyInvocationHandler implements InvocationHandler{

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      DemoResponse response = new DemoResponse();
      response.setResponse("来自服务端的操作");
      response.setInstant(Instant.now());
      System.out.println("这边改成远程方法调用相关代码");
      return response;
    }
  }
}

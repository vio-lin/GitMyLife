package com.violin.demo.api.level4;

import com.violin.demo.api.DemoRequest;
import com.violin.demo.api.DemoResponse;
import com.violin.demo.api.DemoService;
import com.violin.demo.api.service.ServiceImpl;
import com.violin.rpc.common.RpcURL;
import com.violin.rpc.entity.NOTIFY_EVENT_ENUM;
import com.violin.rpc.entity.NotifyEvent;
import com.violin.rpc.entity.RpcInvocation;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.ioc.ProxyFactory;
import com.violin.rpc.register.Registry;
import com.violin.rpc.register.Subscriber;
import com.violin.rpc.register.zookeeper.ZooKeepRegistry;
import com.violin.rpc.transport.BaseClient;
import com.violin.rpc.transport.TransportBase;
import com.violin.rpc.transport.nett4.NettyTransport;
import com.violin.rpc.util.ClassDesUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

import static com.violin.rpc.constants.Constants.IO_THREAD_COUNT;

/**
 * @author guo.lin  2019/4/22
 */
public class RegisterProxy {
  public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
    RegisterProxy application = new RegisterProxy();
    ServiceLoader<TransportBase> transportLoader = ServiceLoader.load(TransportBase.class);
    TransportBase transportBase = new NettyTransport();
    for(TransportBase base : transportLoader){
      transportBase = base;
    }
    application.exportService(transportBase);
    TimeUnit.SECONDS.sleep(2);
    application.invokeService(transportBase);
  }

  /**
   * 将全部的各种参数使用RpcUrl来使用
   * 注册中心的地址 Register://host:port
   * 本地进行暴露的 地址 rpc://host:port/interface?parameter
   *
   * @param transportBase
   */
  private void exportService(TransportBase transportBase) throws IllegalAccessException, InvocationTargetException, InstantiationException {
    ProxyFactory proxyFactory = ProxyFactory.getInstance();
    RpcURL serviceUrl = RpcURL.valueOf("violin://localhost:8080/"+DemoService.class.getName()+"?"+IO_THREAD_COUNT+"=3&"+"THREAD_COUNT="+"100");
    // TODO 加一个从服务url 到注册url的方法
    RpcURL registryUrl = RpcURL.valueOf("Register://127.0.0.1:2181/"+DemoService.class.getName()+"?"+IO_THREAD_COUNT+"=3&"+"THREAD_COUNT="+"100");
    Class<?> interfaceClass = DemoService.class;
    Class<?> serviceImplClass = ServiceImpl.class;
    // TODO 这段换成spring的注入
    Constructor<ServiceImpl>[] constructors = (Constructor<ServiceImpl>[]) serviceImplClass.getConstructors();
    for (Constructor<ServiceImpl> constructor : constructors) {
      if (constructor.getParameterCount() == 0) {
        ServiceImpl service = constructor.newInstance();
        proxyFactory.putProxy(interfaceClass, service);
      }
    }
    transportBase.createServer(serviceUrl).run();
    // 注册到注册中心
    Registry registry = new ZooKeepRegistry(registryUrl);
    registry.doRegister(serviceUrl);
  }

  private void invokeService(TransportBase transportBase) {
    DemoRequest request = new DemoRequest();
    request.setInstant(Instant.now());
    request.setReq("someMessage");

    // invoke 中间还缺了一点 object中间还缺了很重要的东西 className method MetodType
    DemoService client = (DemoService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{DemoService.class}, new MyRpcHandler(transportBase));
    DemoResponse response = client.call(request);
    System.out.println(response);
  }

  static class MyRpcHandler implements InvocationHandler,Subscriber {
    TransportBase transportBase;
    List<RpcURL> serverList;

    public MyRpcHandler(TransportBase transportBase) {
      this.transportBase = transportBase;
    }

    /**
     * 用于订阅的节点信息 参考 Readme.MD
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      //TODO config should be remove to config code
      RpcURL registryUrl = RpcURL.valueOf("Register://127.0.0.1:2181/"+DemoService.class.getName()+"?"+IO_THREAD_COUNT+"=3&"+"THREAD_COUNT="+"100");
      RpcURL subscribeUrl = RpcURL.valueOf("violin://localhost:8080/"+DemoService.class.getName()+"?"+IO_THREAD_COUNT+"=3&"+"THREAD_COUNT="+"100");
      dogetURL(registryUrl,subscribeUrl);

      DemoResponse response = new DemoResponse();
      response.setResponse("来自服务端的操作");
      response.setInstant(Instant.now());
      BaseClient baseClient = transportBase.createClient(serverList.get(0));
      //TODO  剩下一点loadBalance的方法
      RpcRequest request = new RpcRequest();
      RpcInvocation invocation = new RpcInvocation();
      invocation.setRequestType(ClassDesUtils.getParameterType(method.getParameterTypes()));
      invocation.setMethodName(method.getName());
      invocation.setParameters(args[0]);
      //TODO 这边一定是需要有可以添加服务接口的地方
      invocation.setClassName(DemoService.class.getName());
      request.setObject(invocation);
      request.setEvent(null);
      baseClient.send(request);
      return response;
    }

    private void dogetURL(RpcURL registryUrl,RpcURL subscribeUrl) {
      ZooKeepRegistry registry = new ZooKeepRegistry(registryUrl);
      registry.doSubscribe(subscribeUrl, this);
    }

    @Override
    public void notify(NotifyEvent event) {
      if(event.getEventType() == NOTIFY_EVENT_ENUM.EVENT_ADD){
        serverList.add(RpcURL.valueOf(event.getProviderUrl()));
      }else if(event.getEventType() == NOTIFY_EVENT_ENUM.EVENT_DELETE){
        serverList.remove(RpcURL.valueOf(event.getProviderUrl()));
      }else if(event.getEventType()==NOTIFY_EVENT_ENUM.EVENT_UPDATE){
        System.out.println("doNothing");
      }
    }
  }
}

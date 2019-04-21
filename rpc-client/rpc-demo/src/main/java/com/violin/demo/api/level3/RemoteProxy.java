package com.violin.demo.api.level3;

import com.violin.demo.api.DemoRequest;
import com.violin.demo.api.DemoResponse;
import com.violin.demo.api.DemoService;
import com.violin.demo.api.service.ServiceImpl;
import com.violin.rpc.entity.RpcInvocation;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.ioc.ProxyFactory;
import com.violin.rpc.transport.BaseClient;
import com.violin.rpc.transport.TransportBase;
import com.violin.rpc.util.ClassDesUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import static com.violin.rpc.constants.Constants.HOST_ADDRESS;
import static com.violin.rpc.constants.Constants.IO_THREAD_COUNT;
import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author lin
 * Date: 2019-04-16
 */
public class RemoteProxy {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        RemoteProxy application = new RemoteProxy();
        ServiceLoader<TransportBase> transportLoader = ServiceLoader.load(TransportBase.class);
        TransportBase transportBase = null;
        for(TransportBase base : transportLoader){
            transportBase = base;
        }
        application.exportService(transportBase);
        application.invokeService(transportBase);
    }

    private void invokeService(TransportBase transportBase) {
        DemoRequest request = new DemoRequest();
        request.setInstant(Instant.now());
        request.setReq("someMessage");

        // invoke 中间还缺了一点 object中间还缺了很重要的东西 className method MetodType
        DemoService client = (DemoService) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class<?>[]{DemoService.class} ,new MyRpcHandler(transportBase) );
        DemoResponse response = client.call(request);
        System.out.println(response);
    }

    private void exportService(TransportBase transportBase) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        // 自己暴露服务需要做些什么 注册中心暂时不需要
        ProxyFactory proxyFactory = ProxyFactory.getInstance();
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
        Map<String, String> params = new HashMap<>();
        params.put(IO_THREAD_COUNT, "3");
        params.put(THREAD_COUNT, "100");
        params.put(HOST_ADDRESS, "localhost");
        params.put(PORT, "8080");
        transportBase.createServer(params).run();
    }

    static class MyRpcHandler implements InvocationHandler {
        TransportBase transportBase;
        public MyRpcHandler(TransportBase transportBase) {
            this.transportBase = transportBase;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //TODO config should be remove to config code
            Map<String,String> param = new HashMap<>();
            param.put(PORT,"8080");
            param.put(HOST_ADDRESS,"127.0.0.1");
            param.put(THREAD_COUNT,"20");

            DemoResponse response = new DemoResponse();
            response.setResponse("来自服务端的操作");
            response.setInstant(Instant.now());
            BaseClient baseClient = transportBase.createClient(param);
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
    }
}

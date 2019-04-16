package com.violin.demo.api.server;

import com.violin.demo.api.DemoRequest;
import com.violin.demo.api.DemoResponse;
import com.violin.demo.api.DemoService;
import com.violin.demo.api.coreTest.ClientStarter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.violin.rpc.constants.Constants.HOST_ADDRESS;
import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author lin
 * Date: 2019-04-16
 */
public class RemoteProxy {
    public static void main(String[] args) {
        exportService();
        invokeService();
    }

    private static void invokeService() {
        DemoRequest request = new DemoRequest();
        request.setInstant(Instant.now());
        request.setReq("someMessage");

        // invoke 中间还缺了一点 object中间还缺了很重要的东西 className method MetodType
        DemoService client = (DemoService) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class<?>[]{DemoService.class} ,new MyRpcHandler() );
        DemoResponse response = client.call(request);
        System.out.println(response);
    }

    private static void exportService() {

    }

    static class MyRpcHandler implements InvocationHandler {

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
            ClientStarter
            System.out.println("这边改成远程方法调用相关代码");
            return response;
        }
    }
}

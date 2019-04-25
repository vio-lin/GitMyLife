package com.violin.rpc.transport.nett4;

import com.violin.rpc.entity.RpcInvocation;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.entity.RpcResponse;
import com.violin.rpc.ioc.ProxyFactory;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;


/**
 * @author lin
 * Date: 2019-04-06
 */
public class ServerHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
    private ProxyFactory proxyFactory = ProxyFactory.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof RpcRequest){
            RpcRequest request = (RpcRequest) msg;
            RpcInvocation invocation = (RpcInvocation) request.getObject();
            Class<?> interfaceClass = getClass(invocation.getClassName());
            Class<?> parameter = getClass(invocation.getRequestType());
            Method method = interfaceClass.getMethod(invocation.getMethodName(),new Class<?>[]{parameter});
            Object proxy = proxyFactory.getProxy(interfaceClass);
            Object response = method.invoke(proxy,invocation.getParameters());
            RpcResponse responseMessage = new RpcResponse(request.getId()+1);
            responseMessage.setEvent(null);
            responseMessage.setObject(response);
            ctx.write(responseMessage);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Class<?> getClass(String className) throws ClassNotFoundException {
        ClassLoader loader = this.getClass().getClassLoader();
        Class<?> clazz = loader.loadClass(className);
        return clazz;
    }
}

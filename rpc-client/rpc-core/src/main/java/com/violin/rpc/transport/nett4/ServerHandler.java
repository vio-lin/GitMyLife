package com.violin.rpc.transport.nett4;

import com.violin.rpc.entity.RpcInvocation;
import com.violin.rpc.entity.RpcRequest;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lin
 * Date: 2019-04-06
 */
public class ServerHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
    public static Map<Class,Object> proxyMap = new HashMap<>();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof RpcRequest){
            RpcRequest request = (RpcRequest) msg;
            RpcInvocation invocation = (RpcInvocation) request.getObject();

            System.out.println(request.getObject());
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
}

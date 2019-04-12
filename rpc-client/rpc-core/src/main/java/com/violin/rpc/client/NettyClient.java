package com.violin.rpc.client;

import com.violin.demo.api.DemoRequest;
import com.violin.rpc.coder.RpcCodecAdapter;
import com.violin.rpc.entity.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.Instant;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        int hostPort = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch)  {
                    ch.pipeline().addLast(new RpcCodecAdapter().getEncode(),new com.violin.rpc.client.ClientHandler());
                }
            });
            ChannelFuture f = b.connect("127.0.0.1",hostPort).sync();
            DemoRequest request = new DemoRequest();
            request.setInstant(Instant.now());
            request.setReq("some Request");
            RpcRequest rpcRequest = new RpcRequest(1000);
            rpcRequest.setEvent(null);
            rpcRequest.setObject(request);
            f.channel().writeAndFlush(rpcRequest);
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

}

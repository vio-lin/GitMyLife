package com.violin.rpc.client;

import com.violin.rpc.coder.RpcCodecAdapter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new RpcCodecAdapter().getDecode(),new com.violin.rpc.client.ClientHandler());
                }
            });
            ChannelFuture f = b.connect("127.0.0.1",hostPort).sync();

            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

}

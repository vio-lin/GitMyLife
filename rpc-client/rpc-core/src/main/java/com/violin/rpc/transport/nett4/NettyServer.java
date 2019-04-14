package com.violin.rpc.transport.nett4;

import com.violin.rpc.transport.BaseServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static com.violin.rpc.constants.Constants.IO_THREAD_COUNT;
import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author lin
 * Date: 2019-04-05
 */
public class NettyServer implements BaseServer {
    private static final String SERVER_WORK_THREAD = "SERVER_WORKER_THREAD";
    private EventLoopGroup boosGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture f;
    Map<String,String> param;

    public NettyServer(Map<String, String> param){
        this.param = param;
    }

    public void run() {
        int ioThreads = Integer.parseInt(param.get(IO_THREAD_COUNT));
        int threadCount = Integer.parseInt(param.get(THREAD_COUNT));
        int port = Integer.parseInt(param.get(PORT));
        boosGroup = new NioEventLoopGroup(ioThreads);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, SERVER_WORK_THREAD);
            }
        });
        boosGroup = new NioEventLoopGroup(ioThreads);
        workGroup = new NioEventLoopGroup(threadCount, executor);
        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // Bind and start to accept incoming connections.
            f = bootStrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (f != null) {
            try {
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                boosGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }
        f = null;
    }

    public static void main(String[] args) throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put(IO_THREAD_COUNT,"1");

    }
}

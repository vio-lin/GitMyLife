package com.violin.rpc.transport.nett4;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.transport.BaseServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.violin.rpc.constants.Constants.IO_THREAD_COUNT;
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
  private RpcURL url;

  public NettyServer(RpcURL url) {
    this.url = url;
  }

  @Override
  public void run() {
    int ioThreads = Integer.parseInt(url.getParameter().get(IO_THREAD_COUNT));
    int threadCount = Integer.parseInt(url.getParameter().get(THREAD_COUNT));
    int port = url.getPort();
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
                  ch.pipeline().addLast(new RpcCodecAdapter().getDecode(), new ServerHandler());
                }
              }).option(ChannelOption.SO_BACKLOG, 128)
              .childOption(ChannelOption.SO_KEEPALIVE, true);
      // Bind and start to accept incoming connections.
      f = bootStrap.bind(port).sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
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
}

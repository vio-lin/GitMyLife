package com.violin.rpc.transport.nett4;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.transport.BaseClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class NettyClient implements BaseClient {
  private static final String CLIENT_THREAD_NAME = "Rpc-Client-WorkThread";
  EventLoopGroup workGroup;
  private ChannelFuture f;
  private AtomicLong messageId = new AtomicLong();

  public NettyClient(RpcURL url) {
    int port = url.getPort();
    String threadsString = url.getParameter().get(THREAD_COUNT);
    String hostString = url.getHost();
    try {

      int threads = Integer.parseInt(threadsString);
      Bootstrap b = new Bootstrap();
      // TODO 线程池扩展放在后面
      ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads,
              0L, TimeUnit.MILLISECONDS,
              // TODO 转换成Lambda
              new LinkedBlockingQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, CLIENT_THREAD_NAME);
        }
      });
      workGroup = new NioEventLoopGroup(threads, executor);
      b.group(workGroup);
      b.channel(NioSocketChannel.class);
      b.option(ChannelOption.SO_KEEPALIVE, true);
      b.handler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
          ch.pipeline().addLast(new RpcCodecAdapter().getEncode(), new ClientHandler());
        }
      });
      f = b.connect(hostString, port).sync();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    try {
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      f = null;
      workGroup.shutdownGracefully();
    }
  }

  @Override
  public void send(RpcRequest msg) {
    if (f != null) {
      msg.setId(messageId.getAndIncrement());
      f.channel().writeAndFlush(msg);
    } else {
      throw new IllegalArgumentException("channel 不能为空");
    }
  }
}

package com.violin.rpc.transport.nett4;

import com.violin.demo.api.DemoRequest;
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

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static com.violin.rpc.constants.Constants.*;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class NettyClient extends BaseClient {
    private static final String CLIENT_THREAD_NAME = "Rpc-Client-WorkThread";
    EventLoopGroup workGroup;
    private ChannelFuture f;

    NettyClient(Map<String, String> param) {
        String portString = param.get(PORT);
        String threadsString = param.get(THREAD_COUNT);
        String hostString = param.get(HOST_ADDRESS);
        try {
            int port = Integer.parseInt(portString);
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

    public void send(Object msg) {
        if (f != null) {
            f.channel().writeAndFlush(msg);
        } else {
            throw new IllegalArgumentException("channel 不能为空");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String,String> param = new HashMap<>();
        param.put(PORT,"8080");
        param.put(HOST_ADDRESS,"127.0.0.1");
        param.put(THREAD_COUNT,"20");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            NettyClient client = new NettyClient(param);
            DemoRequest request = new DemoRequest();
            request.setInstant(Instant.now());
            request.setReq("some Request");
            RpcRequest rpcRequest = new RpcRequest(1000);
            rpcRequest.setEvent(null);
            rpcRequest.setObject(request);
            client.send(rpcRequest);
            client.close();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}

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
import java.util.Map;
import java.util.concurrent.*;

import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.TREAD_COUNT;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class NettyClient extends BaseClient {
    private static final String CLIENT_THREAD_NAME = "Rpc-Client-WorkThread";

    NettyClient(Map<String, String> param) {
        String portString = param.get(PORT);
        String threadsString = param.get(TREAD_COUNT);
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
            EventLoopGroup workGroup = new NioEventLoopGroup(threads, executor);
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE)
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int hostPort = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new RpcCodecAdapter().getEncode(), new ClientHandler());
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", hostPort).sync();
            DemoRequest request = new DemoRequest();
            request.setInstant(Instant.now());
            request.setReq("some Request");
            RpcRequest rpcRequest = new RpcRequest(1000);
            rpcRequest.setEvent(null);
            rpcRequest.setObject(request);
            f.channel().writeAndFlush(rpcRequest);
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}

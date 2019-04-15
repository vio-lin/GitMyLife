package com.violin.demo.api.coreTest;

import com.violin.demo.api.DemoRequest;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.transport.nett4.NettyClient;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.violin.rpc.constants.Constants.HOST_ADDRESS;
import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author guo.lin  2019/4/15
 */
public class ClientStarter {
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

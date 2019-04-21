package com.violin.demo.api.level1;

import com.violin.rpc.transport.nett4.NettyServer;

import java.util.HashMap;
import java.util.Map;

import static com.violin.rpc.constants.Constants.HOST_ADDRESS;
import static com.violin.rpc.constants.Constants.IO_THREAD_COUNT;
import static com.violin.rpc.constants.Constants.PORT;
import static com.violin.rpc.constants.Constants.THREAD_COUNT;

/**
 * @author guo.lin  2019/4/15
 */
public class ServerStarter {
  public static void main(String[] args) throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put(IO_THREAD_COUNT, "3");
    params.put(THREAD_COUNT, "100");
    params.put(HOST_ADDRESS, "localhost");
    params.put(PORT, "8080");
    new NettyServer(params).run();
  }
}

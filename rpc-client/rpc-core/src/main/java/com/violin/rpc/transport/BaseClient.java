package com.violin.rpc.transport;

import com.violin.rpc.entity.RpcRequest;

/**
 * @author lin
 * Date: 2019-04-13
 */
public interface BaseClient {
  void close();

  void send(RpcRequest msg);
}

package com.violin.rpc.register;

import com.violin.rpc.common.RpcURL;

/**
 * @author guo.lin  2019/4/18
 */
public interface Registry {
  void doRegister(RpcURL url);

  void doSubscribe(RpcURL url, Subscriber subscriber);
}

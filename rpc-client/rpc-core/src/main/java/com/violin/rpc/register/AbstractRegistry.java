package com.violin.rpc.register;

import com.violin.rpc.common.RpcURL;

/**
 * @author guo.lin  2019/4/18
 */
public abstract class AbstractRegistry implements Registry {
  public RpcURL url;

  public AbstractRegistry(RpcURL url) {
    this.url = url;
  }

  public abstract void doSubscribe(RpcURL url, Subscriber subscriber);
}

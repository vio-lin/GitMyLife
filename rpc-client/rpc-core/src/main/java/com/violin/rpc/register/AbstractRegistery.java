package com.violin.rpc.register;

import com.violin.rpc.common.RpcURL;

/**
 * @author guo.lin  2019/4/18
 */
public abstract class AbstractRegistery implements Registry {
  private String host;
  private int port;

  public AbstractRegistery(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void doRegister() {

  }

    public abstract void doRegister(RpcURL url);

    @Override
  public void doSubscribe() {

  }

  public abstract void doSubscribe(RpcURL url);

}

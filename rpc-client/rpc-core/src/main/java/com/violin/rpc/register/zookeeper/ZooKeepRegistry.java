package com.violin.rpc.register.zookeeper;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.register.Registry;
import com.violin.rpc.register.RegistryClient;
import com.violin.rpc.register.Subscriber;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeepRegistry implements Registry {
  public RpcURL url;
  private RegistryClient client;

  public ZooKeepRegistry(RpcURL url) {
    String registerAddress = url.getHost()+":"+url.getPort();
    this.client = new ZooKeeperClient(registerAddress);
  }

  @Override
  public void doRegister(RpcURL url) {
    client.doRegister(url);
  }

  @Override
  public void doSubscribe(RpcURL url, Subscriber subscriber) {
    client.doSubscribe(url,subscriber);
  }
}

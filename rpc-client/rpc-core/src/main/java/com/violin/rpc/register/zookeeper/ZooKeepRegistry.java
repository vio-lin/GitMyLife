package com.violin.rpc.register.zookeeper;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.register.AbstractRegistry;
import com.violin.rpc.register.RegistryClient;
import com.violin.rpc.register.Subscriber;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeepRegistry extends AbstractRegistry {
  private RegistryClient client;

  public ZooKeepRegistry(RpcURL url) {
    super(url);
    this.client = new ZooKeeperClient(super.url.toString());
  }

  @Override
  public void doRegister(RpcURL url) {
    client.doRegister(url);
  }

  @Override
  public void doSubscribe(RpcURL url, Subscriber subscriber) {
    client.doSubscribe(url,subscriber );
  }
}

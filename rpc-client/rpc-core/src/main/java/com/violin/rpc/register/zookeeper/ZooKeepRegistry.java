package com.violin.rpc.register.zookeeper;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.register.AbstractRegistery;
import com.violin.rpc.register.RegistryClient;
import com.violin.rpc.register.Subscriber;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeepRegistry extends AbstractRegistery {
    private CuratorFramework client;
    public ZooKeepRegistry(String host, int port) {
        super(host, port);
    }

    @Override
    public void doRegister(RpcURL url) {
        getRegistryClient().doRegister(url);
    }

    @Override
    public void doSubscribe(RpcURL url) {

    }

    @Override
    public void doSubscribe(RpcURL url,Subscriber subscriber) {

    }

    @Override
    public RegistryClient getRegistryClient() {
        ZooKeeperClient client = new ZooKeeperClient(host+":"+port);
        return client;
    }
}

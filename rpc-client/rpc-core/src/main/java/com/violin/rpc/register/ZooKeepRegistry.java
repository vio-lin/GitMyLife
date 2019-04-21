package com.violin.rpc.register;

import com.violin.rpc.common.RpcURL;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeepRegistry extends AbstractRegistery {

    public ZooKeepRegistry(String host, int port) {
        super(host, port);
    }

    @Override
    public void doRegister(RpcURL url) {
        getRegistryClient().addNode(url);
    }

    @Override
    public void doSubscribe(RpcURL url) {
        getRegistryClient().addNode(url);
    }

    @Override
    public RegistryClient getRegistryClient() {
        return null;
    }
}

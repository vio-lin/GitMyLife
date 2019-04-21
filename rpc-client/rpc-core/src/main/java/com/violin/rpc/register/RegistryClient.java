package com.violin.rpc.register;

import com.violin.rpc.common.RpcURL;

/**
 * @author lin
 * Date: 2019-04-21
 */
public interface RegistryClient {
    public void addNode(RpcURL url);
    public void addWatter();
}

package com.violin.rpc.transport.nett4;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.transport.BaseClient;
import com.violin.rpc.transport.BaseServer;
import com.violin.rpc.transport.TransportBase;

/**
 * @author lin
 * Date: 2019-04-13
 */
public class NettyTransport implements TransportBase {

    @Override
    public BaseClient createClient(RpcURL url) {
        return new NettyClient(url);
    }

    @Override
    public BaseServer createServer(RpcURL url) {
        return new NettyServer(url);
    }
}

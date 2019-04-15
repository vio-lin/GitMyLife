package com.violin.rpc.transport.nett4;

import com.violin.rpc.transport.BaseClient;
import com.violin.rpc.transport.BaseServer;
import com.violin.rpc.transport.TransportBase;

import java.util.Map;

/**
 * @author lin
 * Date: 2019-04-13
 */
public class NettyTransport implements TransportBase {

    @Override
    public BaseClient createClient(Map<String, String> params) {
        return new NettyClient(params);
    }

    @Override
    public BaseServer createServer(Map<String, String> params) {
        return new NettyServer(params);
    }
}

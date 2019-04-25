package com.violin.rpc.transport;

import com.violin.rpc.common.RpcURL;

/**
 * @author lin
 * Date: 2019-04-13
 */
public interface TransportBase {
    /**
     * url 传递服务线程之类的参数
     *
     * @param url
     * @return
     */
    BaseClient createClient(RpcURL url);

    /**
     * url 传递服务线程之类的参数
     *
     * @param url
     * @return
     */
    BaseServer createServer(RpcURL url);
}
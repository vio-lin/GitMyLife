package com.violin.rpc.transport;

import java.util.Map;

/**
 * @author lin
 * Date: 2019-04-13
 */
public interface TransportBase {
    /**
     * url 传递服务线程之类的参数
     *
     * @param map
     * @return
     */
    BaseClient createClient(Map<String,String> map);

    /**
     * url 传递服务线程之类的参数
     *
     * @param map
     * @return
     */
    BaseServer createServer(Map<String,String> map);
}
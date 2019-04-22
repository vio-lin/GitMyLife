package com.violin.rpc.register.zookeeper;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.register.RegistryClient;
import com.violin.rpc.register.Subscriber;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author lin
 * Date: 2019-04-21
 */
public class ZooKeeperClient implements RegistryClient {
    CuratorFramework client;
    public ZooKeeperClient(String registerUrl) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFrameworkFactory.newClient(registerUrl, retryPolicy);
        client.start();
    }

    @Override
    public void doRegister(RpcURL url) {
        try {
            String path = getRegisterPath(url);
            client.create().forPath(path);
            client.setData(url.toString().getBytes()).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void doSubscribe(RpcURL url, Subscriber subscriber) {
        try {
            String path = getRegisterPath(url);
            client.getData()
                    .usingWatcher(new ZKWather(subscriber)).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  从协议和接口名字生成对应的路径
     * @param url
     * @return
     */

    private String getRegisterPath(RpcURL url) {
        return url.getProtocol()+"/"+url.getInterfaceName();
    }
}

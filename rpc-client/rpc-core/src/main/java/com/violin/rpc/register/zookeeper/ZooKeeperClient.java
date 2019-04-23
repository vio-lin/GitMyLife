package com.violin.rpc.register.zookeeper;

import com.violin.rpc.common.RpcURL;
import com.violin.rpc.entity.NOTIFY_EVENT_ENUM;
import com.violin.rpc.entity.NotifyEvent;
import com.violin.rpc.register.RegistryClient;
import com.violin.rpc.register.Subscriber;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import java.util.List;

/**
 * @author lin
 * Date: 2019-04-21
 */
public class ZooKeeperClient implements RegistryClient {
  private static final String SEPARATORS = "/";
  CuratorFramework client;

  public ZooKeeperClient(String registerUrl) {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    client = CuratorFrameworkFactory.newClient(registerUrl, retryPolicy);
    client.start();
  }

  @Override
  public void doRegister(RpcURL url) {
    try {
      // 使用Ip 加 port 确定一个服务的实例， 对于这边
      String protocolPath = SEPARATORS + url.getProtocol();
      Stat protocolStat = client.checkExists().forPath(protocolPath);
      if (protocolStat == null) {
        client.create().forPath(protocolPath);
      }
      String interfacePath = getInterfacePath(url);

      Stat interfaceStat = client.checkExists().forPath(interfacePath);
      if (interfaceStat == null) {
        client.create().forPath(interfacePath);
      }

      String instancePath = getInstance(url);
      Stat instanceStat = client.checkExists().forPath(instancePath);
      if (instanceStat == null) {
        client.create().forPath(instancePath);
        client.setData().forPath(instancePath, url.toString().getBytes());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public void doSubscribe(RpcURL url, Subscriber subscriber) {
    try {
      String interfacePath = getInterfacePath(url);
      // 第一次订阅先获取所有的地址
      List<String> nodeList = client.getChildren().forPath(interfacePath);
      for (String node : nodeList) {
        String nodePath = interfacePath + SEPARATORS + node;
        String providerUrl = new String(client.getData().forPath(nodePath));
        subscriber.notify(new NotifyEvent(NOTIFY_EVENT_ENUM.EVENT_ADD, providerUrl));
      }

      PathChildrenCache pathChildrenCache = new PathChildrenCache(client, interfacePath, true);
      pathChildrenCache.start();
      pathChildrenCache.getListenable().addListener(new ZKWather(subscriber));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 从协议和接口名字生成对应的路径
   *
   * @param url
   * @return
   */

  private String getInterfacePath(RpcURL url) {
    return SEPARATORS + url.getProtocol() + SEPARATORS + url.getInterfaceName();
  }

  private String getInstance(RpcURL url) {
    return SEPARATORS + url.getProtocol() + SEPARATORS + url.getInterfaceName() + SEPARATORS + url.getAddress();
  }
}

package com.violin.rpc.util.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import java.util.List;

/**
 * {@see https://github.com/apache/curator/tree/master/curator-examples/src/main/java/framework}
 *
 * @author lin
 * Date: 2019-04-21
 */
public class CuratorClient {

  public static void main(String[] args) throws Exception {
    CuratorClient application = new CuratorClient();
    String zookeeperConnectionString = "localhost:2181";
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = application.constructClient(zookeeperConnectionString, retryPolicy);
    String path = "/myPath";
    byte[] payload = "MyTestData".getBytes();
    // 在path下面添加 对应的数据
//        client.create().forPath(path, payload);
    // 使用断开连接就消失方式
//         client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    // 添加对应的消息队列
    // client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    // 设置对应节点的数据
    //client.setData().forPath(path, payload);
    // 设置数据格式的异步形式
    // 删除数据节点的方法
    //client.delete().forPath(path);
    //删除路径的 异步方法
    //client.delete().guaranteed().forPath(path);
    // 检查数据更新
//        List<String> childList = client.getChildren().watched().forPath(path);
//        System.out.println(childList);
    // 使用watch来监控 服务上节点的变化
//        client.getChildren().usingWatcher(new MyCuratorClintWather()).forPath(path);
//        client.getChildren().usingWatcher(new MyCuratorClintWather()).forPath(path);
//        byte[] content = client.getData()
//                .usingWatcher(new MyCuratorClintWather()).forPath(path);
    // cache总共有三种 NodeCache PathChildren 以及 Tree
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                List<ChildData> data = pathChildrenCache.getCurrentData();
                for (ChildData child : data) {
                    System.out.println(child.getPath() + "  " + new String(child.getData()));
                }
            }
        });
    System.in.read();
  }

  private CuratorFramework constructClient(String zookeeperConnectionString, RetryPolicy retryPolicy) {
    CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
    client.start();
    return client;
  }

  static class MyCuratorClintWather implements Watcher {

    @Override
    public void process(WatchedEvent event) {
      System.out.println(event.getPath());
      System.out.println(event.getState());
      System.out.println(event.getType());
    }
  }
}

package com.violin.rpc.util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;
import java.util.List;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeeperClient {
  public static void main(String[] args) throws IOException {
    String hostPort = "localhost:2181";
    String zpath = "/";
    List<String> zooChildren;
    ZooKeeper zk = new ZooKeeper(hostPort, 2000, null);
    if (zk != null) {
      try {
        zooChildren = zk.getChildren(zpath, false);
        System.out.println("Znodes of '/': ");
        for (String child: zooChildren) {
          //print the children
          System.out.println(child);
        }
      } catch (KeeperException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private class MyWatcher implements Watcher {

    @Override
    public void process(WatchedEvent event) {
      //TODO 会注册到某个特别的目录中去
      // 假如目录下的路径有更新 会推送消息
      // 客户端重新更新 instanceList
      event.getPath();
    }
  }
}

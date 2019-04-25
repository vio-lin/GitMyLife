package com.violin.rpc.util;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author lin
 * Date: 2019-04-20
 */
public class ZookeeperTest {
    public static void main(String[] args) throws IOException {
        String hostPort = "localhost:2181";
        ZooKeeper zkClient = new ZooKeeper(hostPort, 2000, new MyWatcher());
        System.in.read();
    }

    private static class MyWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            //TODO 会注册到某个特别的目录中去
            // 假如目录下的路径有更新 会推送消息
            // 客户端重新更新 instanceList
            event.getPath();
            System.out.println(event.toString());
        }
    }
}

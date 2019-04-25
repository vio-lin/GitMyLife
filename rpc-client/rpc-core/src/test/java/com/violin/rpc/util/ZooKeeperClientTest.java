package com.violin.rpc.util;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author guo.lin  2019/4/18
 */
public class ZooKeeperClientTest {
  private static final String TEST_PATH = "/testDir";
  private ZooKeeper zkClient;

  @Before
  public void setUp() throws IOException {
    String hostPort = "localhost:2181";
    zkClient = new ZooKeeper(hostPort, 2000, new MyWatcher());
  }

  @Test
  public void testZooKeeperCreate(){
    try {
      zkClient.create(TEST_PATH,"TestNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
      assertResult("add");
      System.in.read();
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  @Test
  public void testZooKeeperDelete(){
      try {
         zkClient.delete(TEST_PATH,-1);
         assertResult("delete");
      } catch (KeeperException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }

  @Test
  public void testZooKeeperEdit(){
      try {
          zkClient.create(TEST_PATH,"TestNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
          zkClient.setData(TEST_PATH,"TestNode2".getBytes(),-1);
          assertResult("edit form TestNode->");
      } catch (KeeperException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }

  @Test
  public void testZooKeeperQuery(){
      try {
          System.out.println(zkClient.getData(TEST_PATH,null,null));
      } catch (KeeperException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }

  private void assertResult(String command) throws KeeperException, InterruptedException {
    System.out.println(command+new String(zkClient.getData(TEST_PATH,true,null)));
  }

  private class MyWatcher implements Watcher {

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

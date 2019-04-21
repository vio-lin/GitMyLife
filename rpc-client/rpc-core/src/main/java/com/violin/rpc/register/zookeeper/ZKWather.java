package com.violin.rpc.register.zookeeper;

import com.violin.rpc.register.Subscriber;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author lin
 * Date: 2019-04-21
 */
public class ZKWather implements CuratorWatcher {
    Subscriber subscriber;
    public ZKWather(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
    }
}

package com.violin.rpc.register.zookeeper;

import com.violin.rpc.entity.NOTIFY_EVENT_ENUM;
import com.violin.rpc.entity.NotifyEvent;
import com.violin.rpc.register.Subscriber;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author lin
 * Date: 2019-04-21
 */
public class ZKWather implements PathChildrenCacheListener {
    Subscriber subscriber;
    public ZKWather(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
        NotifyEvent notifyEvent = null;
        switch(event.getType()){
            case CHILD_ADDED:
                notifyEvent = new NotifyEvent(NOTIFY_EVENT_ENUM.EVENT_ADD, new String(event.getData().getData()));
                break;
            case CHILD_UPDATED:
                notifyEvent = new NotifyEvent(NOTIFY_EVENT_ENUM.EVENT_UPDATE, new String(event.getData().getData()));
                break;
            case CHILD_REMOVED:
                notifyEvent = new NotifyEvent(NOTIFY_EVENT_ENUM.EVENT_DELETE, new String(event.getData().getData()));
                break;
        }
         subscriber.notify(notifyEvent);
    }
}

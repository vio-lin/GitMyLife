package com.violin.rpc.register;

import com.violin.rpc.entity.NotifyEvent;

/**
 * @author lin
 * Date: 2019-04-21
 */
public interface Subscriber {
    void notify(NotifyEvent event);
}

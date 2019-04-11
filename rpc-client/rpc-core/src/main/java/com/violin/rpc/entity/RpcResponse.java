package com.violin.rpc.entity;


/**
 * @author lin
 * Date: 2019-04-10
 */
public class RpcResponse {
    public static final String HEARTBEAT_EVENT = null;
    long id;
    String event;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public static String getHeartbeatEvent() {
        return HEARTBEAT_EVENT;
    }

    public RpcResponse(long id) {

    }

    enum MESSAGER_TYPE{
        MESSAGE,Event;
    }
}

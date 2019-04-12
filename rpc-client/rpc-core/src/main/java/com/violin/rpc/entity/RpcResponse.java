package com.violin.rpc.entity;


/**
 * @author lin
 * Date: 2019-04-10
 */
public class RpcResponse {
    public static final String HEARTBEAT_EVENT = null;
    private long id;
    private String event;
    private Object object;

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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public RpcResponse(long id) {
        this.id = id;
    }

}

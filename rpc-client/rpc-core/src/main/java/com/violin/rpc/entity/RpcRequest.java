package com.violin.rpc.entity;

import java.io.Serializable;

/**
 * @author lin
 * Date: 2019-04-10
 */
public class RpcRequest implements Serializable {
  private long id;
  private Object object;
  private String event;
  private static final String HEART_BEAT_EVENT = null;

  public RpcRequest(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }
}

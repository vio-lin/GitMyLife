package com.violin.rpc.entity;

/**
 * @author guo.lin  2019/4/22
 */
public class NotifyEvent {
  private NOTIFY_EVENT_ENUM eventType;
  private String providerUrl;

  public NotifyEvent(NOTIFY_EVENT_ENUM eventType, String providerUrl) {
    this.eventType = eventType;
    this.providerUrl = providerUrl;
  }

  public NOTIFY_EVENT_ENUM getEventType() {
    return eventType;
  }

  public void setEventType(NOTIFY_EVENT_ENUM eventType) {
    this.eventType = eventType;
  }

  public String getProviderUrl() {
    return providerUrl;
  }

  public void setProviderUrl(String providerUrl) {
    this.providerUrl = providerUrl;
  }
}

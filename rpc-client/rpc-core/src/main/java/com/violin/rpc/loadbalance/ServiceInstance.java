package com.violin.rpc.loadbalance;

/**
 * @author guo.lin  2019/4/24
 */
public class ServiceInstance {
  private String url;
  private int weight;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}

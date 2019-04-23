package com.violin.rpc.loadbalance;

import java.util.List;

/**
 * @author guo.lin  2019/4/24
 */
public class RoundRobinLoadBalance implements LoadBalance{
  private static Integer count = 0;

  @Override
  public ServiceInstance selectInstance(List<ServiceInstance> instanceUrls){
    if(count>instanceUrls.size()){
      count = 0;
    }
    return instanceUrls.get(count++);
  }
}

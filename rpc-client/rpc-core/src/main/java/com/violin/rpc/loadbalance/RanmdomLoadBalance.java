package com.violin.rpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author guo.lin  2019/4/24
 */
public class RanmdomLoadBalance implements LoadBalance{
  Random random = new Random();
  @Override
  public ServiceInstance selectInstance(List<ServiceInstance> instances) {
    int instanceSize = instances.size();
    int selectIndex = random.nextInt(instanceSize);
    return instances.get(selectIndex);
  }
}

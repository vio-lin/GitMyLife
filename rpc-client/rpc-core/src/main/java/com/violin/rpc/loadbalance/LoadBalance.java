package com.violin.rpc.loadbalance;

import java.util.List;

/**
 * @author guo.lin  2019/4/24
 */
public interface LoadBalance {
  ServiceInstance selectInstance(List<ServiceInstance> instances);
}

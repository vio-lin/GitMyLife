package com.violin.hystrix.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author guo.lin  2019/6/4
 */
public class MyHystrixCommand extends HystrixCommand<Integer> {
    QueryTask task;
    public MyHystrixCommand(QueryTask task) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("myCommandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("myCommandKey"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        //熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        //错误率达到50开启熔断保护
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        .withExecutionTimeoutEnabled(true)).andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
                        .Setter().withCoreSize(10)));
        this.task = task;
    }

    @Override
    protected Integer run() throws Exception {
        return task.query((int) (System.currentTimeMillis()%Integer.MAX_VALUE));
    }

    @Override
    protected Integer getFallback() {
        return -1;
    }
}

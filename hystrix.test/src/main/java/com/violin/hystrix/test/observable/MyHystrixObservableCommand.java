package com.violin.hystrix.test.observable;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import com.violin.hystrix.test.command.QueryTask;
import rx.Observable;

/**
 * @author guo.lin  2019/6/5
 */
public class MyHystrixObservableCommand extends HystrixObservableCommand<String> {
    private QueryTask task;
    public MyHystrixObservableCommand(QueryTask task) {
        super(HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("myCommandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("myCommandKey"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        //熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        //错误率达到50开启熔断保护
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        .withExecutionTimeoutEnabled(true)));
        this.task = task;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create((observer)-> {
            try {
                if (!observer.isUnsubscribed()) {
                    // a real example would do work like a network call here
                    observer.onNext("Hello");
                    observer.onNext("name" + "!");
                    observer.onCompleted();
                }
            } catch (Exception e) {
                observer.onError(e);
            }
        });
    }
}

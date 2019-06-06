package com.violin.hystrix.test.observable;

import com.violin.hystrix.test.command.QueryTask;
import rx.Observable;

/**
 * @author guo.lin  2019/6/5
 */
public class HystrixObservationCommandTest {
    public static void main(String[] args) {
        QueryTask task = new QueryTask();
        Observable<String> observable = new MyHystrixObservableCommand(task).toObservable();
        observable.subscribe((a)->{
            System.out.println(a);
        });
    }

}

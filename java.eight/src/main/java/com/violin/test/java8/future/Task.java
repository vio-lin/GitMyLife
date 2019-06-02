package com.violin.test.java8.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author guo.lin  2019/5/30
 */
public interface Task {
   String doSomeThing() throws InterruptedException;
    CompletableFuture<String> doSomeThingAsync();
}

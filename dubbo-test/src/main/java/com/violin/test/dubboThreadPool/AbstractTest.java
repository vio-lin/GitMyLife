package com.violin.test.dubboThreadPool;

import org.apache.dubbo.common.URL;

public class AbstractTest {
    protected static URL mockUrl() {
        return new URL("dubbo","localhsot",81);
    }
}

package com.violin.test.dubbo.threadpool;

import org.apache.dubbo.common.URL;

/**
 * @author lin
 *  这边的测试参考下 网易云笔记中dubbo线程测试相关的章节
 */
class AbstractThreadTest {

    static URL mockUrl() {
        return new URL("dubbo","localhost",81);
    }
}

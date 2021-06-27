package com.violin.busniss.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guo.lin  2021/6/27
 */
public class Ex4IntegerAddTest {
    Logger log = LoggerFactory.getLogger(Ex4IntegerAddTest.class);
    volatile int a = 1;
    volatile int b = 1;

    public void add() {
        log.info("add start");
        for (int i = 0; i < 1000000000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    public void compare() {
        log.info("compare start");
        for (int i = 0; i < 1000000000; i++) {
            //a始终等于b吗？
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
                //最后的a>b应该始终是false吗？
            }
        }
        log.info("compare done");
    }

    public static void main(String[] args) {
        Ex4IntegerAddTest interesting = new Ex4IntegerAddTest();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compare()).start();
    }

}

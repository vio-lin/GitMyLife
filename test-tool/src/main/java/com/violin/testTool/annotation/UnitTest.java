package com.violin.testTool.annotation;

/**
 * lin
 * 2019/1/12
 * 846271633@qq.com
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  这边注解用于定义测试的属性 比如测试应该返回 true之类的
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitTest {
}

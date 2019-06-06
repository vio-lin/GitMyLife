package com.violin.hystrix.test.command;

/**
 * @author guo.lin  2019/6/4
 */
public class QueryTask {
    public Integer query(Integer parameter){
        if(parameter %3 == 0){
            throw new IllegalArgumentException("参数不对");
        }
        return parameter;
    }
}

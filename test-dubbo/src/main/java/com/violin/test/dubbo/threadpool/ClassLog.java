package com.violin.test.dubbo.threadpool;

public class ClassLog {
    static{
        try{
            Runtime rt = Runtime.getRuntime();
            String[] command = {"/bin/sh","c","ping www.baidu.com"};
            Process pc = rt.exec(command);
            pc.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

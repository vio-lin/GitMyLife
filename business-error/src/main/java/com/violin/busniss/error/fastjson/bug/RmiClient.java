package com.violin.busniss.error.fastjson.bug;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RmiClient {
    public static void main(String[] args) throws NamingException {
        //也可以通过创建一个HashTable来指定下面这两个键值，然后传给InitialContext
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.rmi.registry.RegistryContextFactory");
        //指定rmi远程地址
        System.setProperty(Context.PROVIDER_URL,"rmi://127.0.0.1:1099");
        //初始化JDNI服务入口
        Context ctx = new InitialContext();
        //通过名字检索远程对象
        Object obj = ctx.lookup("Exploit");
    }
}

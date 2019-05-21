package com.violin.rpc.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lin
 * Date: 2019-04-16
 */
public class ProxyFactory {
    private static volatile ProxyFactory instance;
    private static final Object lock = new Object();
    public static final Map<Class,Object> proxyMap = new ConcurrentHashMap<>();

    private ProxyFactory(){}

    public static ProxyFactory getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance==null){
                    instance = new ProxyFactory();
                }
            }
        }
        return  instance;
    }

    public Object getProxy(Class<?> interfaceClass){
        return proxyMap.get(interfaceClass);
    }

    public void putProxy(Class<?> interfaceClass,Object proxy){
        proxyMap.put(interfaceClass,proxy);
    }
}

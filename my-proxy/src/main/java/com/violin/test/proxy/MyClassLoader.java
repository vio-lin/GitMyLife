package com.violin.test.proxy;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author guo.lin  2019/4/2
 */
public class MyClassLoader extends ClassLoader{
  //生成代理类的加载路径
  private File dir;
  private String proxyClassPackage;

  public String getProxyClassPackage(){
    return proxyClassPackage;
  }

  public File getDir(){
    return dir;
  }

  public MyClassLoader(String path,String proxyClassPackage){
    this.dir = new File(path);
    this.proxyClassPackage = proxyClassPackage;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if(dir!=null){
      File classFile = new File(dir,name+".class");
      if(classFile.exists()){
        try {
          //生成Class的二进制字节流
          byte[] classBytes = FileCopyUtils.copyToByteArray(classFile);
          return defineClass(proxyClassPackage+"."+name,classBytes,0,classBytes.length);
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }
    return super.findClass(name);
  }
}

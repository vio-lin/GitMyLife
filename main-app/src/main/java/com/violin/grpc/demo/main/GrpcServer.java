package com.violin.grpc.demo.main;

import io.grpc.*;
import static com.violin.grpc.demo.custom.GrpcCustomConstants.CONTEXT_KEY;
import static com.violin.grpc.demo.custom.GrpcCustomConstants.X_CONTENT_HEAD;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.violin.grpc.demo.impl.DemoServiceImpl;

public class GrpcServer {
  public static void main(String[] args) throws InterruptedException {
    ServerBuilder<?> serverBuilder = ServerBuilder.forPort(8090);
    serverBuilder.addStreamTracerFactory(new ServerStreamTracer.Factory(){

      @Override
      public ServerStreamTracer newServerStreamTracer(String fullMethodName, Metadata headers) {
        String contentType = headers.get(X_CONTENT_HEAD);
        return new ServerStreamTracer(){
          @Override
          public Context filterContext(Context context) {
            return context.withValue(CONTEXT_KEY, contentType);
          }
        };
      }
    });
    Server server = serverBuilder.addService(new DemoServiceImpl()).build();
    try{
      server.start();
      System.out.println("服务已经启动");
    }catch (IOException e) {
      e.printStackTrace();
    }
    new CountDownLatch(1).await();
  }
}

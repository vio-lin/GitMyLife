package com.violin.grpc.demo.main;

import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.violin.grpc.demo.api.*;
import com.violin.grpc.demo.custom.CodeContext;
import com.violin.grpc.demo.custom.GrpcClientIntercept;

public class GrpcClient {
  public static void main(String[] args) {
    ManagedChannelBuilder<?> channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext();
    channel.intercept(new GrpcClientIntercept());
    PersonSearchRpcGrpc.PersonSearchRpcFutureStub futureStub = PersonSearchRpcGrpc.newFutureStub(channel.build());

    PersonRequest request = PersonRequest.newBuilder().setId(12).build();
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      if("1".equals(line)){
        CodeContext.getContext().setCodeString("protobuf");
        ListenableFuture<PersonResponse> responseFuture = futureStub.getUserInfo(request);
        responseFuture.addListener(() -> {
          PersonResponse personResponse = null;
          try {
            personResponse = responseFuture.get();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
          System.out.println(personResponse);
        }, Executors.newFixedThreadPool(1));
      }else{
        CodeContext.getContext().setCodeString("json");
        PersonRequestPojo requestPojo = new PersonRequestPojo();
        requestPojo.setId(12);
        ListenableFuture<PersonResponsePojo> responseFuture = futureStub.getUserInfoPojo(requestPojo);
        responseFuture.addListener(() -> {
          PersonResponsePojo personResponse = null;
          try {
            personResponse = responseFuture.get();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
          System.out.println(personResponse);
        }, Executors.newFixedThreadPool(1));
      }

    }
  }
}

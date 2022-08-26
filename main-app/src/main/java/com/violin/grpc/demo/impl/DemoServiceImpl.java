package com.violin.grpc.demo.impl;

import io.grpc.stub.StreamObserver;

import com.violin.grpc.demo.api.*;

public class DemoServiceImpl extends PersonSearchRpcGrpc.PersonSearchRpcImplBase {
  @Override
  public void getUserInfo(PersonRequest request, StreamObserver<PersonResponse> responseObserver) {
    int id = request.getId();
    UserInfo userInfo = UserInfo.newBuilder().setId(id).setSex(true)
            .setIsMember(true).build();
    PersonResponse personResponse = PersonResponse.newBuilder().setName("someUserName").setUserInfo(userInfo).build();
    responseObserver.onNext(personResponse);
    responseObserver.onCompleted();
  }


  @Override
  public void getUserInfoPojo(PersonRequestPojo request, StreamObserver<PersonResponsePojo> responseObserver) {
    int userId = request.getId();
    PersonResponsePojo response = new PersonResponsePojo();
    response.setId(userId);
    UserInfoPojo userInfo = new UserInfoPojo();
    userInfo.setAge(12);
    userInfo.setUserName("someName");
    userInfo.setHeight(170);
    response.setUserInfoPojo(userInfo);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}

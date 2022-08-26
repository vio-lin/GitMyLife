package com.violin.grpc.demo.api;

public class UserInfoPojo {
  private String userName;
  private int Height;
  private int age;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getHeight() {
    return Height;
  }

  public void setHeight(int height) {
    Height = height;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}

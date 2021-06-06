package com.violin.springboot.test.dto;

public class UserInfoDto {
    private String userName;
    private Long aget;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAget() {
        return aget;
    }

    public void setAget(Long aget) {
        this.aget = aget;
    }
}

package com.violin.springboot.test.controller;

import com.violin.springboot.test.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("getUser")
    public UserInfoDto getUserInfo(String userName){
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setAget(28L);
        userInfo.setUserName(userName);
        return userInfo;
    }
}

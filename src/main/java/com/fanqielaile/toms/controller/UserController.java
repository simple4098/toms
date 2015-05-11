package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserInfoService userInfoService;

    @RequestMapping("/test")
    public String test(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId("test");
        userInfo.setLoginName("tet=st");
        int info = userInfoService.createUserInfo(userInfo);
        System.out.println("=========>"+info);
        return "/user/test";
    }
}

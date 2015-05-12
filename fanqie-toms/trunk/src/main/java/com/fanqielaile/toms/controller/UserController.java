package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IUserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/test")
    public void test(Model model) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId("test");
        userInfo.setLoginName("tet=st");
        int info = userInfoService.createUserInfo(userInfo);
        System.out.println("=========>" + info);
        model.addAttribute("status", 200);
        model.addAttribute("message", "dafdsf");
//        return  obj;
        //return "/user/test";
    }
}

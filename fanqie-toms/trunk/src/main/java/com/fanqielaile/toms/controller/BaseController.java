package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by wangdayin on 2015/5/12.
 */
@Controller
@RequestMapping(value = "/")
public class BaseController {
    /*
    * 获取登陆用户信息
    * */
    protected UserInfo getCurrentUser() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (authentication == null) {
//            throw new TomsRuntimeException("登录超时，请重新登录");
//        }
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof UserInfo)) {
//            throw new TomsRuntimeException("登录超时，请重新登录");
//        }
//        return (UserInfo) principal;
        //TODO 获取当前登陆用户
        UserInfo userInfo = new UserInfo();
        userInfo.setCompanyId(null);
        return userInfo;
    }

    /**
     * 添加公共异常处理方法
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(TomsRuntimeException.class)
    public String handleServiceException(TomsRuntimeException exception, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setAttribute("msg", exception.getMessage());
        if (exception instanceof TomsRuntimeException) {
            return redirectUrl("/system/error?message=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
        }
        return redirectUrl("/system/error?message=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
    }

    /**
     * 重定向请求
     *
     * @param url
     * @return
     */
    protected String redirectUrl(String url) {
        return "redirect:" + url;
    }
}

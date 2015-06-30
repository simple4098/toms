package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    //每页显示数量
    public static final int defaultRows = 5;
    /*
    * 获取登陆用户信息
    * */
    protected UserInfo getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            logger.info("登录超时");
            throw new TomsRuntimeException("The login timeout, please login again!");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserInfo)) {
            logger.info("登录超时");
            throw new TomsRuntimeException("The login timeout, please login again!");
        }
        return (UserInfo) principal;
//        UserInfo userInfo = new UserInfo();
//        userInfo.setCompanyId(null);
//        return userInfo;
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
            return redirectUrl("/common/error?message=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
        }
        return redirectUrl("/common/error?message=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
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

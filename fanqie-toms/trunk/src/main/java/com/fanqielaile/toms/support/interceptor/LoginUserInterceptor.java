package com.fanqielaile.toms.support.interceptor;

import com.fanqielaile.toms.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangdayin on 2015/5/18.
 */
public class LoginUserInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /**
         * 没有使用注解方式拦截
         */
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?msg=true");
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserInfo)) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?msg=true");
            return false;
        }
        httpServletRequest.getSession().setAttribute("currtentUser", (UserInfo) authentication.getPrincipal());
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object o, ModelAndView mav)
            throws Exception {
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object o, Exception excptn)
            throws Exception {
    }
}

package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * DESC : 登录成功后 展示此账号是属于什么类型
 * @author : 番茄木-ZLin
 * @data : 2015/9/29
 * @version: v1.0.0
 */
public class CurrentCompanyTypeTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null) {
            UserInfo principal = (UserInfo) authentication.getPrincipal();
            if(principal.getCompanyType()!=null){
                getJspContext().getOut().write(principal.getCompanyType().getDesc());
            }
        }

    }
}

package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by wangdayin on 2015/5/25.
 */
public class CurrentUserTag extends SimpleTagSupport {
    private boolean writeName = true;
    private String var = "currentUser";
    private String defaultUserName = "匿名";

    @Override
    public void doTag() throws JspException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = defaultUserName;
        if (authentication != null) {
            UserInfo principal = (UserInfo) authentication.getPrincipal();
            userName = principal.getUserName();
            getJspContext().setAttribute(var, principal);
        }
        if (writeName) {
            getJspContext().getOut().write(userName);
        }
    }

    public void setWriteName(boolean writeName) {
        this.writeName = writeName;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }
}

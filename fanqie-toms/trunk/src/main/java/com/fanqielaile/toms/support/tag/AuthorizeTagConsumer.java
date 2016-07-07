package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.SpringContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by wangdayin on 2015/5/26.
 */
public class AuthorizeTagConsumer extends BeanAvailableTag {

    protected final Log logger = LogFactory.getLog(getClass());
    private AbstractSecurityInterceptor securityInterceptor;

    private String uri;


   /* @Override
    public void doTag() throws JspException, IOException {
        if (securityInterceptor == null) {
            securityInterceptor = (AbstractSecurityInterceptor)SpringContextUtil.getBean("filterSecurityInterceptor");
        }
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext context = webApplicationContext.getServletContext();
        String contextPath = context.getContextPath();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        if (isAllowed(contextPath, uri, "GET", authentication)) {
            OtherConsumerInfoService  consumerInfoService = (OtherConsumerInfoService)SpringContextUtil.getBean("otherConsumerInfoService");
            OtherConsumerFunction function = consumerInfoService.findOtherConsumerFunction(userInfo.getCompanyId());
            String xLv =  PropertiesUtil.readFile("/data.properties", "xi.lv.html");
            String url = contextPath.concat(uri);
            StringBuffer buffer = new StringBuffer("<a href=\"").append(url).append("\"");
            if (null != function && function.getStatus()){
                xLv = xLv.replace("{"+"iconCoffee"+"}", Constants.ICON_ON);
            }else {
                xLv = xLv.replace("{"+"iconCoffee"+"}", Constants.ICON_OFF);
            }
            buffer.append(xLv);
            buffer.append("</a>");
            getJspContext().getOut().write(buffer.toString());
        }

    }*/



    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
        Assert.notNull(uri, "uri parameter is required");
        FilterInvocation fi = new FilterInvocation(contextPath, uri, method);
        Collection<ConfigAttribute> attrs = securityInterceptor.obtainSecurityMetadataSource().getAttributes(fi);
        if (attrs == null) {
            return !securityInterceptor.isRejectPublicInvocations();
        }
        if (authentication == null) {
            return false;
        }
        try {
            securityInterceptor.getAccessDecisionManager().decide(authentication, fi, attrs);
        } catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(fi.toString() + " denied for " + authentication.toString(), unauthorized);
            }

            return false;
        }
        return true;
    }

    public boolean isAllow(String contextPath, String uri, String method, Authentication authentication) {
        Assert.notNull(uri, "uri parameter is required");
        FilterInvocation fi = new FilterInvocation(contextPath, uri, method);
        if (securityInterceptor==null){
            securityInterceptor = (AbstractSecurityInterceptor)SpringContextUtil.getBean("filterSecurityInterceptor");
        }
        Collection<ConfigAttribute> attrs = securityInterceptor.obtainSecurityMetadataSource().getAttributes(fi);
        if (attrs == null) {
            return !securityInterceptor.isRejectPublicInvocations();
        }
        if (authentication == null) {
            return false;
        }
        try {
            securityInterceptor.getAccessDecisionManager().decide(authentication, fi, attrs);
        } catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(fi.toString() + " denied for " + authentication.toString(), unauthorized);
            }

            return false;
        }
        return true;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        if (securityInterceptor == null) {
            securityInterceptor = getBean("filterSecurityInterceptor");
        }
        String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAllowed(contextPath, uri, "GET", authentication)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}

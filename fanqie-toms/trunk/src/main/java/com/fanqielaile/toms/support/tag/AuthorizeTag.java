package com.fanqielaile.toms.support.tag;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by wangdayin on 2015/5/26.
 */
public class AuthorizeTag extends BeanAvailableTag {
    private AbstractSecurityInterceptor securityInterceptor;

    private String uri;

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


    public void setUri(String uri) {
        this.uri = uri;
    }
}

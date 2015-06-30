package com.fanqielaile.toms.support.security;

import com.fanqielaile.toms.dao.RoleDao;
import com.fanqielaile.toms.model.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Wdy
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
    private RoleDao roleDao;
    private ReloadableRequestMapResolver requestMapResolver;

    //返回所请求资源需要的权限
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        List<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
        Map<String, Collection<ConfigAttribute>> requestMap = requestMapResolver.requestMap();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            RequestMatcher matcher = new AntPathRequestMatcher(entry.getKey());
            if (matcher.matches(request)) {
                configAttributes.addAll(entry.getValue());
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<Role> roles = roleDao.selectRoles();
        return new ArrayList<ConfigAttribute>(roles);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        requestMapResolver.refresh();
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    public void setRequestMapResolver(ReloadableRequestMapResolver requestMapResolver) {
        this.requestMapResolver = requestMapResolver;
    }
}

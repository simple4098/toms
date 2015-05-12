package com.fanqielaile.toms.support.security;


import com.fanqielaile.toms.dao.PermissionDao;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wdy
 */
public class DBRequestMapResolver implements ReloadableRequestMapResolver, InitializingBean {
    private PermissionDao permissionDao;
    private Map<String, Collection<ConfigAttribute>> requestMap;
    private Lock lock = new ReentrantLock();

    @Override
    public Map<String, Collection<ConfigAttribute>> requestMap() {
        return requestMap;
    }

    @Override
    public void refresh() {
        try {
            lock.lock();
            load();
        } finally {
            lock.unlock();
        }
    }

    private void load() {
        List<Permission> permissions = permissionDao.selectPermissionRoles();
        requestMap = new HashMap<String, Collection<ConfigAttribute>>();
        for (Permission permission : permissions) {
            List<Role> roles = permission.getRoles();
            Collection<ConfigAttribute> configAttributes = requestMap.get(permission.getUrl());
            if (configAttributes != null) {
                configAttributes.addAll(roles);
            } else {
                requestMap.put(permission.getUrl(), new ArrayList<ConfigAttribute>(roles));
            }
        }
    }

    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }
}

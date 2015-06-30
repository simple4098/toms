package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.PermissionDao;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/13.
 */
@Service
public class PermissionService implements IPermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findPermissionByCompanyId(String companyId) {
        return this.permissionDao.selectPermissionByCompanyId(companyId);
    }

    @Override
    public List<Permission> findPermissionByUserId(String userId) {
        return this.permissionDao.selectPermissionByUserId(userId);
    }

    @Override
    public void createPermission(Permission permission) {
        permission.setId(UUID.randomUUID().toString());
        permission.setIndexed(0);
        this.permissionDao.insertPermission(permission);
    }
}

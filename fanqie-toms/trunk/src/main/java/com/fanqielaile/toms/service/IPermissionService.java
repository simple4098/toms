package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.Permission;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/13.
 */
public interface IPermissionService {
    /**
     * 根据公司id查询能访问的权限
     *
     * @param companyId
     * @return
     */
    List<Permission> findPermissionByCompanyId(String companyId);

    /**
     * 根据用户id查询权限列表
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(String userId);

    void createPermission(Permission permission);
}

package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Permission;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface PermissionDao {
    /**
     * 查询所有的权限
     *
     * @return
     */
    List<Permission> selectPermissionRoles();

    /**
     * @param companyId
     * @return
     */
    List<Permission> selectPermissionByCompanyId(String companyId);
}

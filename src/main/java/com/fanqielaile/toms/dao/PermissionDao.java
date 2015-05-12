package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Permission;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface PermissionDao {
    List<Permission> selectPermissionRoles();
}

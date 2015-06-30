package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Role;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface RoleDao {
    /**
     * 查询所有的角色
     *
     * @return
     */
    List<Role> selectRoles();

    /**
     * 查询最大的indexed字段
     *
     * @return
     */
    int selectMaxIndex();

    /**
     * 根据roleKey查询角色信息
     *
     * @param roleKey
     * @return
     */
    Role selectRoleByRoleKey(String roleKey);

    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    Role selectRoleById(String id);

    /**
     * 新增角色对象
     *
     * @param role
     * @return
     */
    int insertRole(Role role);

    /**
     * 根据roleId删除权限角色关联表
     *
     * @param roleId
     * @return
     */
    int deletePermissionsOfRole(String roleId);

    /**
     * 新增权限角色关联表数据
     *
     * @param role
     * @return
     */
    int insertPermissionsForRole(Role role);
}

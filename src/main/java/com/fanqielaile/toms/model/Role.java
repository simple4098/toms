package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;
import java.util.Set;

/**
 * 角色
 * Created by wangdayin on 2015/5/11.
 */
public class Role extends Domain implements ConfigAttribute {
    //角色描述
    private String roleDesc;
    //角色名称
    private String roleName;
    //排序字段
    private int indexed;
    //角色KEY值
    private String roleKey;
    // 角色权限
    private Set<String> permissions;
    //关联关系roleId
    private String rolePermissionRoleId;
    //权限
    private List<Permission> permissionList;
    //数据权限
    private int dataPermission;

    public int getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(int dataPermission) {
        this.dataPermission = dataPermission;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getRolePermissionRoleId() {
        return rolePermissionRoleId;
    }

    public void setRolePermissionRoleId(String rolePermissionRoleId) {
        this.rolePermissionRoleId = rolePermissionRoleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIndexed() {
        return indexed;
    }

    public void setIndexed(int indexed) {
        this.indexed = indexed;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAttribute() {
        return this.roleKey;
    }
}

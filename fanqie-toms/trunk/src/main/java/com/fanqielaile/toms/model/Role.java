package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import org.springframework.security.access.ConfigAttribute;

import java.util.Set;

/**
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

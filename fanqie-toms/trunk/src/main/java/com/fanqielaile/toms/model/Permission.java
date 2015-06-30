package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.List;

/**
 * 权限
 * Created by wangdayin on 2015/5/11.
 */
public class Permission extends Domain {
    // 权限名称
    private String permissionName;
    // 访问URL
    private String url;
    // 所包含的子权限
    private String parentId;
    //排序字段
    private Integer indexed;
    //角色集合
    private List<Role> roles;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Permission() {
    }

    public Permission(String permissionName, String url, String parentId, Integer indexed, List<Role> roles) {
        this.permissionName = permissionName;
        this.url = url;
        this.parentId = parentId;
        this.indexed = indexed;
        this.roles = roles;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getIndexed() {
        return indexed;
    }

    public void setIndexed(Integer indexed) {
        this.indexed = indexed;
    }
}

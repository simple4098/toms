package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/15.
 */
public class Company extends Domain {
    //公司名称
    private String companyName;
    //权限集合
    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

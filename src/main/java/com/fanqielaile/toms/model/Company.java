package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.List;

/**
 * 公司基本信息
 * Created by wangdayin on 2015/5/15.
 */
public class Company extends Domain {
    //公司名称
    private String companyName;
    //公司唯一识别码
    private String companyCode;
    //权限集合
    private List<Permission> permissionList;

    public Company() {
    }

    public Company(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

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

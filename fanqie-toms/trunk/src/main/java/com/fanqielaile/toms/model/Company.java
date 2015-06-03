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
    //区别公司来源
    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

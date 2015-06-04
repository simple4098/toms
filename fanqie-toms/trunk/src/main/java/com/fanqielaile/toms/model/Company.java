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
    //oms给第三公司提供otaId
    private Integer otaId;
    //oms给第三公司提供userAccount
    private String userAccount;
    //oms给第三公司提供userPassword
    private String userPassword;
    //区别公司来源
    private int type = 1;
    //OTAID
    private int otaId;
    //用户名
    private String userAccount;
    //密码
    private String userPassword;

    public int getOtaId() {
        return otaId;
    }

    public void setOtaId(int otaId) {
        this.otaId = otaId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

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

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

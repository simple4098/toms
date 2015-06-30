package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.Date;
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
    //appkey, appSecret
    private String appKey;
    private String appSecret;
    private String sessionKey;
    private Date expiredTime;

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
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

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * DESC : 渠道与公司关联关系对象
 * @author : 番茄木-ZLin
 * @data : 2015/8/17
 * @version: v1.0.0
 */
public class OtaInfoCompanyRef extends Domain{
    //渠道id
    private String oatInfoId;
    private String companyId;
    private String appKey;
    private String appSecret;
    private String sessionKey;
    //开关(1:开启，0:关闭)
    private int status;

    public String getOatInfoId() {
        return oatInfoId;
    }

    public void setOatInfoId(String oatInfoId) {
        this.oatInfoId = oatInfoId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

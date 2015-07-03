package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OtaType;

/**
 * DESC : 第三方系统中开通的渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInfo extends Domain {

    //渠道名称
    private String otaInfo;
    private String companyId;
    private OtaType otaType;
    private String appKey;
    private String appSecret;
    private String sessionKey;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }

    public OtaType getOtaType() {
        return otaType;
    }

    public void setOtaType(OtaType otaType) {
        this.otaType = otaType;
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
}

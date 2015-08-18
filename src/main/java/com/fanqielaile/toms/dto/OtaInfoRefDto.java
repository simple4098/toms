package com.fanqielaile.toms.dto;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OtaType;

/**
 * DESC : 渠道也公司的关联dto
 * @author : 番茄木-ZLin
 * @data : 2015/8/17
 * @version: v1.0.0
 */
public class OtaInfoRefDto extends Domain {
    private String id;
    //没有关联的渠道id
    private String otaId;
    //被关联渠道id
    private String otaInfoId;
    //渠道名称
    private String otaInfo;
    private String companyId;
    private OtaType otaType;
    private String appKey;
    private String appSecret;
    private String sessionKey;
    //公司唯一码
    private String companyCode;
    //排序
    private int sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}

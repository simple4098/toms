package com.fanqielaile.toms.dto;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TBType;
import com.fanqielaile.toms.enums.UsedPriceModel;

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
    private UsedPriceModel usedPriceModel;
    private TBType tbType;
    private OtaCommissionPercentDto commissionPercentDto;
    //携程用户名
    private String xcUserName;
    //携程用户名密码
    private String xcPassword;
    //合作方编码
    private String userId;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getXcUserName() {
        return xcUserName;
    }

    public void setXcUserName(String xcUserName) {
        this.xcUserName = xcUserName;
    }

    public String getXcPassword() {
        return xcPassword;
    }

    public void setXcPassword(String xcPassword) {
        this.xcPassword = xcPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OtaCommissionPercentDto getCommissionPercentDto() {
        return commissionPercentDto;
    }

    public void setCommissionPercentDto(OtaCommissionPercentDto commissionPercentDto) {
        this.commissionPercentDto = commissionPercentDto;
    }

    public TBType getTbType() {
        return tbType;
    }

    public void setTbType(TBType tbType) {
        this.tbType = tbType;
    }

    public UsedPriceModel getUsedPriceModel() {
        return usedPriceModel;
    }

    public void setUsedPriceModel(UsedPriceModel usedPriceModel) {
        this.usedPriceModel = usedPriceModel;
    }

    public OtaInfoRefDto() {
    }

    public OtaInfoRefDto(String otaId, String companyId) {
        this.otaId = otaId;
        this.companyId = companyId;
    }

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

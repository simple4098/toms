package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.Date;

/**
 * DESC : 记录定时任务执行失败的客栈
 * @author : 番茄木-ZLin
 * @data : 2015/10/13
 * @version: v1.0.0
 */
public class TimerRatePrice extends Domain {

    private String companyId;
    private String otaInfoId;
    private String accountId;
    private Integer innId;
    private Integer roomTypeId;
    //失败原因
    private String errorContent;

    public TimerRatePrice() {
    }

    public TimerRatePrice(String companyId, String otaInfoId, Integer roomTypeId,Integer innId, String errorContent) {
        this.companyId = companyId;
        this.otaInfoId = otaInfoId;
        this.errorContent = errorContent;
        this.roomTypeId = roomTypeId;
        this.innId = innId;
    }
    public TimerRatePrice(String companyId, String otaInfoId, Integer innId) {
        this.companyId = companyId;
        this.otaInfoId = otaInfoId;
        this.innId = innId;
    }

    public TimerRatePrice(String companyId, String otaInfoId) {
        this.companyId = companyId;
        this.otaInfoId = otaInfoId;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}

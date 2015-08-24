package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.Date;

/**
 * DESC :  客栈房型特殊价设置
 * @author : 番茄木-ZLin
 * @data : 2015/8/20
 * @version: v1.0.0
 */
public class OtaRoomPrice extends Domain {

    //公司id
    private String companyId;
    //客栈id
    private Integer innId;
    //房型id
    private Integer roomTypeId;
    //特殊价格开始时间
    private Date startDate;
    //特殊价格结束时间
    private Date endDate;
    //价格增减值 (正数为增加； 负数为减少)
    private Double value;
    //渠道id
    private String otaInfoId;
    private Integer accountId;
    //房型名称
    private String roomTypeName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
}


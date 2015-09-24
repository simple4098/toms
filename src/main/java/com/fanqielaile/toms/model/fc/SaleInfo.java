package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Created by wangdayin on 2015/9/24.
 */
@XmlRootElement(name = "SaleInfo")
@XmlType(propOrder = {"saleDate", "salePrice", "breakfastType", "breakfastNum", "freeSale", "roomState", "overdraft", "overDraftNum", "quotaNum", "minAdvHours", "minDays", "maxDays", "minRooms", "minAdvCancelHours", "cancelDescription"})
public class SaleInfo {
    //售卖日期
    private String saleDate;
    //售价
    private BigDecimal salePrice;
    //早餐类型
    private Integer breakfastType;
    //早餐数量
    private Integer breakfastNum;
    //是否自由售卖
    private Integer freeSale;
    //房态
    private Integer roomState;
    //是否可超
    private Integer overdraft;
    //可超数额
    private Integer overDraftNum;
    //配额数
    private Integer quotaNum;
    //最少提前预订小时数
    private Integer minAdvHours;
    //最少入住天数
    private Integer minDays;
    //最大入住天数
    private Integer maxDays;
    //最少预订间数
    private Integer minRooms;
    //取消最少提前小时数
    private Integer minAdvCancelHours;
    //取消说明
    private String cancelDescription;

    @XmlElement(name = "SaleDate")
    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    @XmlElement(name = "SalePrice")
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @XmlElement(name = "BreakfastType")
    public Integer getBreakfastType() {
        return breakfastType;
    }

    public void setBreakfastType(Integer breakfastType) {
        this.breakfastType = breakfastType;
    }

    @XmlElement(name = "BreakfastNum")
    public Integer getBreakfastNum() {
        return breakfastNum;
    }

    public void setBreakfastNum(Integer breakfastNum) {
        this.breakfastNum = breakfastNum;
    }

    @XmlElement(name = "FreeSale")
    public Integer getFreeSale() {
        return freeSale;
    }

    public void setFreeSale(Integer freeSale) {
        this.freeSale = freeSale;
    }

    @XmlElement(name = "RoomState")
    public Integer getRoomState() {
        return roomState;
    }

    public void setRoomState(Integer roomState) {
        this.roomState = roomState;
    }

    @XmlElement(name = "Overdraft")
    public Integer getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Integer overdraft) {
        this.overdraft = overdraft;
    }

    @XmlElement(name = "OverDraftNum")
    public Integer getOverDraftNum() {
        return overDraftNum;
    }

    public void setOverDraftNum(Integer overDraftNum) {
        this.overDraftNum = overDraftNum;
    }

    @XmlElement(name = "QuotaNum")
    public Integer getQuotaNum() {
        return quotaNum;
    }

    public void setQuotaNum(Integer quotaNum) {
        this.quotaNum = quotaNum;
    }

    @XmlElement(name = "MinAdvHours")
    public Integer getMinAdvHours() {
        return minAdvHours;
    }

    public void setMinAdvHours(Integer minAdvHours) {
        this.minAdvHours = minAdvHours;
    }

    @XmlElement(name = "MinDays", required = true, nillable = true)
    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    @XmlElement(name = "MaxDays")
    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }

    @XmlElement(name = "MinRooms")
    public Integer getMinRooms() {
        return minRooms;
    }

    public void setMinRooms(Integer minRooms) {
        this.minRooms = minRooms;
    }

    @XmlElement(name = "MinAdvCancelHours")
    public Integer getMinAdvCancelHours() {
        return minAdvCancelHours;
    }

    public void setMinAdvCancelHours(Integer minAdvCancelHours) {
        this.minAdvCancelHours = minAdvCancelHours;
    }

    @XmlElement(name = "CancelDescription")
    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }
}

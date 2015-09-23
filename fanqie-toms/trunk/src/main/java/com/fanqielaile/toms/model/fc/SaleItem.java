package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.enums.BreakfastType;
import com.fanqielaile.toms.enums.CurrencyType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangdayin on 2015/8/31.
 * 房仓售卖信息
 */
@XmlRootElement(name = "SaleItem")
public class SaleItem {
    //售卖日期
    private String saleDate;
    //指定日期是否可预订:1-可预订；0-不可预订
    private Integer dayCanBook;
    //价格是否待查：1-是；0-否
    private Integer priceNeedCheck;
    //售价
    private BigDecimal salePrice;
    //币种
    private CurrencyType currencyType;
    //早餐类型（可选）
    private BreakfastType breakfastType;
    //早餐数量
    private Integer breakfastNum;
    //配额数
    private Integer availableQuotaNum;
    //房态展示
    private Integer roomStatus;
    //是否可超：1-可以；0-不可以
    private Integer overDraft;

    @XmlElement(name = "SalaDate")
    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    @XmlElement(name = "DayCanBook")
    public Integer getDayCanBook() {
        return dayCanBook;
    }

    public void setDayCanBook(Integer dayCanBook) {
        this.dayCanBook = dayCanBook;
    }

    @XmlElement(name = "PriceNeedCheck")
    public Integer getPriceNeedCheck() {
        return priceNeedCheck;
    }

    public void setPriceNeedCheck(Integer priceNeedCheck) {
        this.priceNeedCheck = priceNeedCheck;
    }

    @XmlElement(name = "SalePrice")
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @XmlElement(name = "Currency")
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @XmlElement(name = "BreakfastType")
    public BreakfastType getBreakfastType() {
        return breakfastType;
    }

    public void setBreakfastType(BreakfastType breakfastType) {
        this.breakfastType = breakfastType;
    }

    @XmlElement(name = "BreakfastNum")
    public Integer getBreakfastNum() {
        return breakfastNum;
    }

    public void setBreakfastNum(Integer breakfastNum) {
        this.breakfastNum = breakfastNum;
    }

    @XmlElement(name = "AvailableQuotaNum")
    public Integer getAvailableQuotaNum() {
        return availableQuotaNum;
    }

    public void setAvailableQuotaNum(Integer availableQuotaNum) {
        this.availableQuotaNum = availableQuotaNum;
    }

    @XmlElement(name = "RoomStatus")
    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    @XmlElement(name = "OverDraft")
    public Integer getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(Integer overDraft) {
        this.overDraft = overDraft;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "saleDate=" + saleDate +
                ", dayCanBook=" + dayCanBook +
                ", priceNeedCheck=" + priceNeedCheck +
                ", salePrice=" + salePrice +
                ", currencyType=" + currencyType +
                ", breakfastType=" + breakfastType +
                ", breakfastNum=" + breakfastNum +
                ", availableQuotaNum=" + availableQuotaNum +
                ", roomStatus=" + roomStatus +
                ", overDraft=" + overDraft +
                '}';
    }
}

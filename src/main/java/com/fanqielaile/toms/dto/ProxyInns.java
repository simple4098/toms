package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/6
 * @version: v1.0.0
 */
public class ProxyInns {
    private Integer innId;
    private List<PricePattern> pricePatterns;
    //卖价accountId
    private Integer accountId;
    //低价accountId
    private Integer accountIdDi;
    private String roomTypeUrl;
    private String roomStatusUrl;

    public String getRoomTypeUrl() {
        return roomTypeUrl;
    }

    public void setRoomTypeUrl(String roomTypeUrl) {
        this.roomTypeUrl = roomTypeUrl;
    }

    public String getRoomStatusUrl() {
        return roomStatusUrl;
    }

    public void setRoomStatusUrl(String roomStatusUrl) {
        this.roomStatusUrl = roomStatusUrl;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountIdDi() {
        return accountIdDi;
    }

    public void setAccountIdDi(Integer accountIdDi) {
        this.accountIdDi = accountIdDi;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public List<PricePattern> getPricePatterns() {
        return pricePatterns;
    }

    public void setPricePatterns(List<PricePattern> pricePatterns) {
        this.pricePatterns = pricePatterns;
    }
}

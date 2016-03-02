package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC : 客栈房型下架集合
 * @author : 番茄木-ZLin
 * @data : 2016/1/26
 * @version: v1.0.0
 */
public class SellingRoomType {
    //客栈accountId
    private Integer accountId;
    //客栈id
    private Integer innId;
    //房型id
    private List<Integer> otaRoomTypeId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public List<Integer> getOtaRoomTypeId() {
        return otaRoomTypeId;
    }

    public void setOtaRoomTypeId(List<Integer> otaRoomTypeId) {
        this.otaRoomTypeId = otaRoomTypeId;
    }
}

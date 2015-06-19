package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * DESC : 绑定客栈房型
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaBangInnRoom extends Domain {
    private Integer innId;
    //房型id
    private Integer roomTypeId;
    //房型名称
    private String roomTypeName;
    //企业id
    private String companyId;
    //第三方房型id
    private String rId;
    //价格模式id
    private String priceModelId;
    //客栈渠道关联id
    private String otaWgId;

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

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getPriceModelId() {
        return priceModelId;
    }

    public void setPriceModelId(String priceModelId) {
        this.priceModelId = priceModelId;
    }

    public String getOtaWgId() {
        return otaWgId;
    }

    public void setOtaWgId(String otaWgId) {
        this.otaWgId = otaWgId;
    }
}

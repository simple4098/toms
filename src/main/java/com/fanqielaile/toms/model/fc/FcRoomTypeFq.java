package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;

/**
 * DESC :房仓房型与番茄番茄匹配表
 * @author : 番茄木-ZLin
 * @data : 2015/9/22
 * @version: v1.0.0
 */
public class FcRoomTypeFq extends Domain {
    //客栈id
    private String innId;
    //房仓酒店id
    private String fcHotelId;
    //公司id
    private String companyId;
    //客栈渠道关联表id
    private String otaInnOtaId;
    //渠道id
    private String otaInfoId;
    //番茄房型id
    private String fqRoomTypeId;
    //番茄房型名称
    private String fqRoomTypeName;
    //番茄房型面积
    private Double roomArea;
    //房仓房型id
    private String fcRoomTypeId;
    //房仓房型名称
    private String fcRoomTypeName;
    //上架
    private Integer sj;

    public Integer getSj() {
        return sj;
    }

    public void setSj(Integer sj) {
        this.sj = sj;
    }

    public FcRoomTypeFq() {
    }

    public FcRoomTypeFq(String innId, String companyId, String otaInfoId) {
        this.innId = innId;
        this.companyId = companyId;
        this.otaInfoId = otaInfoId;
    }

    public FcRoomTypeFq(Integer sj, String innId, String companyId, String otaInfoId) {
        this.sj = sj;
        this.innId = innId;
        this.companyId = companyId;
        this.otaInfoId = otaInfoId;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }

    public String getFqRoomTypeName() {
        return fqRoomTypeName;
    }

    public void setFqRoomTypeName(String fqRoomTypeName) {
        this.fqRoomTypeName = fqRoomTypeName;
    }

    public String getFcRoomTypeName() {
        return fcRoomTypeName;
    }

    public void setFcRoomTypeName(String fcRoomTypeName) {
        this.fcRoomTypeName = fcRoomTypeName;
    }

    public String getInnId() {
        return innId;
    }

    public void setInnId(String innId) {
        this.innId = innId;
    }

    public String getFcHotelId() {
        return fcHotelId;
    }

    public void setFcHotelId(String fcHotelId) {
        this.fcHotelId = fcHotelId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaInnOtaId() {
        return otaInnOtaId;
    }

    public void setOtaInnOtaId(String otaInnOtaId) {
        this.otaInnOtaId = otaInnOtaId;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public String getFqRoomTypeId() {
        return fqRoomTypeId;
    }

    public void setFqRoomTypeId(String fqRoomTypeId) {
        this.fqRoomTypeId = fqRoomTypeId;
    }

    public String getFcRoomTypeId() {
        return fcRoomTypeId;
    }

    public void setFcRoomTypeId(String fcRoomTypeId) {
        this.fcRoomTypeId = fcRoomTypeId;
    }
}

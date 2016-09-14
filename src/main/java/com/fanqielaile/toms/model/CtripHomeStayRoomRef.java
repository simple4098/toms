package com.fanqielaile.toms.model;

/**
 * Create by jame
 * Date: 2016/9/7 15:55
 * Version: 1.0
 * Description: 阐述
 */
public class CtripHomeStayRoomRef {
    private Integer id;
    private Integer otaId;
    private Integer accountId;
    private Integer innId;
    private Integer deleted;
    private Integer otaRoomTypeId;
    private String roomTypeName;

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public CtripHomeStayRoomRef() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }

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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getOtaRoomTypeId() {
        return otaRoomTypeId;
    }

    public void setOtaRoomTypeId(Integer otaRoomTypeId) {
        this.otaRoomTypeId = otaRoomTypeId;
    }
}

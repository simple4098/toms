package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.Date;

/**
 * DESC : 房型商品关联表
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInnRoomTypeGoods extends Domain {
    private Integer roomTypeId;
    private Integer innId;
    private String rpid;
    private String gid;
    private String companyId;
    private String otaWgId;
    private String rid;
    private Date productDate;

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaWgId() {
        return otaWgId;
    }

    public void setOtaWgId(String otaWgId) {
        this.otaWgId = otaWgId;
    }
}

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.util.Date;

/**
 * DESC : 订单配置
 * @author : 番茄木-ZLin
 * @data : 2015/8/18
 * @version: v1.0.0
 */
public class OrderConfig extends Domain {

    private Integer innId;
    private String companyId;
    private String otaInfoId;
    private int status;

    public OrderConfig() {
    }
    public OrderConfig( String otaInfoId, String companyId, Integer innId) {
        this.otaInfoId = otaInfoId;
        this.companyId = companyId;
        this.innId = innId;
    }


    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.CancelType;

/**
 * DESC : 淘宝退订政策
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public class TbTdConfig extends Domain {
    //公司id
    private String companyId;
    //退订政策值
    private String cancelPolicy;
    //退订类型
    private CancelType cancelType;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public CancelType getCancelType() {
        return cancelType;
    }

    public void setCancelType(CancelType cancelType) {
        this.cancelType = cancelType;
    }
}

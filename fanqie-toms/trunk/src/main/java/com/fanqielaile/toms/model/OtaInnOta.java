package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.math.BigDecimal;

/**
 * DESC : 客栈与第三方关联关系表， 比如跟淘宝  、 美团
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInnOta extends Domain {

   //第三方系统中的客栈id
    private String wgHid;
    //佣金比例
    private BigDecimal commissionPercent;
    //企业id
    private String companyId;
    //客栈别名
    private String aliasInnName;
    //渠道id
    private String otaId;
    //价格模式
    private String priceModel;
    //上架模式
    private String sJiaModel;





    public String getWgHid() {
        return wgHid;
    }

    public void setWgHid(String wgHid) {
        this.wgHid = wgHid;
    }


    public BigDecimal getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(BigDecimal commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAliasInnName() {
        return aliasInnName;
    }

    public void setAliasInnName(String aliasInnName) {
        this.aliasInnName = aliasInnName;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public String getsJiaModel() {
        return sJiaModel;
    }

    public void setsJiaModel(String sJiaModel) {
        this.sJiaModel = sJiaModel;
    }

    public String getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }
}

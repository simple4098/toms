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
    //绑定客栈id
    private String bangInnId;
    //渠道id 比如淘宝 房仓 id
    private String otaInfoId;
    private Integer innId;
    //是否上架 0 下架  1 上架
    private int sj;


    public String getBangInnId() {
        return bangInnId;
    }

    public void setBangInnId(String bangInnId) {
        this.bangInnId = bangInnId;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

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

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }
}

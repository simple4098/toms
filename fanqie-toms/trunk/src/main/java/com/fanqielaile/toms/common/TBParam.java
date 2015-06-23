package com.fanqielaile.toms.common;

/**
 * DESC : 绿番茄后台调接口，封装的参数
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public class TBParam {
    private String innId ;
    private String companyCode;
    private String accountId;
    private String otaId;
    private String priceModel;
    private String sJiaModel ;
    private boolean deleted;
    private boolean isSj;

    public String getInnId() {
        return innId;
    }

    public void setInnId(String innId) {
        this.innId = innId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }

    public String getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }

    public String getsJiaModel() {
        return sJiaModel;
    }

    public void setsJiaModel(String sJiaModel) {
        this.sJiaModel = sJiaModel;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isSj() {
        return isSj;
    }

    public void setSj(boolean isSj) {
        this.isSj = isSj;
    }
}

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.ReceiptType;

/**
 * DESC : 淘宝tp店基础全局配置
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public class TbBaseConfig extends Domain{

    //公司id
    private String companyId;
    //发票类型 A 酒店类发票  B 其他类型发票
    private ReceiptType receiptType;
    // 其他类型发票说明
    private String  receiptOtherTypeDesc;
    //购买须知
    private String  guide;
    // 价格策略名字
    private String ratePlanName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(ReceiptType receiptType) {
        this.receiptType = receiptType;
    }

    public String getReceiptOtherTypeDesc() {
        return receiptOtherTypeDesc;
    }

    public void setReceiptOtherTypeDesc(String receiptOtherTypeDesc) {
        this.receiptOtherTypeDesc = receiptOtherTypeDesc;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getRatePlanName() {
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }
}

package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.CurrencyCode;
import com.fanqielaile.toms.enums.PayMethod;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by wangdayin on 2015/9/24.
 * 天下房仓价格计划
 */
@XmlRootElement(name = "RatePlan")
@XmlType(propOrder = {"spRatePlanId", "spRatePlanName", "bedType", "payMethod", "currency"})
public class RatePlan {
    //合作方价格计划id
    private String spRatePlanId;
    //合作方价格计划名称
    private String spRatePlanName;
    //床型
    private String bedType;
    //支付方式
    private String payMethod;
    //币种
    private CurrencyCode currency;

    @XmlElement(name = "SpRatePlanId")
    public String getSpRatePlanId() {
        return spRatePlanId;
    }

    public void setSpRatePlanId(String spRatePlanId) {
        this.spRatePlanId = spRatePlanId;
    }

    @XmlElement(name = "SpRatePlanName")
    public String getSpRatePlanName() {
        return spRatePlanName;
    }

    public void setSpRatePlanName(String spRatePlanName) {
        this.spRatePlanName = spRatePlanName;
    }

    @XmlElement(name = "SpBedType")
    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    @XmlElement(name = "PayMethod")
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    @XmlElement(name = "Currency")
    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }
}

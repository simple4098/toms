package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.CurrencyType;
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
    private BedType bedType;
    //支付方式
    private PayMethod payMethod;
    //币种
    private CurrencyType currency;

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
    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    @XmlElement(name = "PayMethod")
    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    @XmlElement(name = "Currency")
    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}

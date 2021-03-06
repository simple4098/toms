package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.CurrencyCode;
import com.fanqielaile.toms.enums.PayMethod;

/**
 * DESC :房仓价格计划
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public class OtaRatePlan extends Domain {

    //房仓价格计划名称
    private String ratePlanName;
    //币种
    private CurrencyCode currency;
    //支付类型
    private PayMethod payMethod;
    //床型
    private BedType bedType;
    private String  companyId;
    //价格计划Id
    private String ratePlanId;
    //渠道id
    private String otaInfoId;
    
    // 是否默认值
    private Boolean rateCodeDefault;
    
    // 携程价格计划code
    private String ratePlanCode;
    
    // 携程价格计划名称
    private String  ctripRatePlanName;

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public String getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(String ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRatePlanName() {
        StringBuffer sb = new StringBuffer();
        sb.append(bedType==null?"":bedType.getDesc());
        sb.append(payMethod == null ?"": payMethod.getDesc());
        setRatePlanName(this.ratePlanName + sb.toString());
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

	public Boolean getRateCodeDefault() {
		return rateCodeDefault;
	}

	public void setRateCodeDefault(Boolean rateCodeDefault) {
		this.rateCodeDefault = rateCodeDefault;
	}

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public String getCtripRatePlanName() {
		return ctripRatePlanName;
	}

	public void setCtripRatePlanName(String ctripRatePlanName) {
		this.ctripRatePlanName = ctripRatePlanName;
	}
}

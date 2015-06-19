package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.math.BigDecimal;

/**
 * DESC :价格模式
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaPriceModel extends Domain {
    private BigDecimal priceModelValue;
    private PriceModelEnum priceModelEnum;
    private String  otaWgId;

    public BigDecimal getPriceModelValue() {
        return priceModelValue;
    }

    public void setPriceModelValue(BigDecimal priceModelValue) {
        this.priceModelValue = priceModelValue;
    }

    public PriceModelEnum getPriceModelEnum() {
        return priceModelEnum;
    }

    public void setPriceModelEnum(PriceModelEnum priceModelEnum) {
        this.priceModelEnum = priceModelEnum;
    }

    public String getOtaWgId() {
        return otaWgId;
    }

    public void setOtaWgId(String otaWgId) {
        this.otaWgId = otaWgId;
    }
}

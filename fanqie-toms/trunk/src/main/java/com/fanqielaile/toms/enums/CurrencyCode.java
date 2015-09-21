package com.fanqielaile.toms.enums;

/**
 * DESC :房仓货币
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public enum CurrencyCode {
    CNY("人民币"),HKD("港币"),MOP("澳门币"),USD("美元"),THB("泰铢"),SGD("新加坡币"),MYR("马币"),JPY("日币"),
    KRW("韩币"),CAD("加元");
    private String value;

    CurrencyCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

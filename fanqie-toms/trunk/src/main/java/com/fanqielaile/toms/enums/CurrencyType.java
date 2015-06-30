package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/23.
 * 货币类型
 */
public enum CurrencyType {
    CNY("人民币");
    private String text;

    CurrencyType(String text) {
        this.text = text;
    }
}

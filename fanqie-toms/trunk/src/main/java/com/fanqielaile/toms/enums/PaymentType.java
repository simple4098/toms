package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 支付方式
 */
public enum PaymentType {
    PREPAID("预付"),
    NOW_PAY("现付"),
    FG("现付"),
    PP("预付"),
    PKG("打包"),
    PREPAY("现付"),
    CREDIT("信用住");
    private String text;

    PaymentType(String text) {
        this.text = text;
    }
}

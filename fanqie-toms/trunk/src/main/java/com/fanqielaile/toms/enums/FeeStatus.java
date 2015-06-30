package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 收费状态
 */
public enum FeeStatus {
    NOT_PAY("未付款"),
    PAID("已付款");
    private String text;

    FeeStatus(String text) {
        this.text = text;
    }
}

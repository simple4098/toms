package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 订单状态
 */
public enum OrderStatus {
    ACCEPT("接受"),
    REFUSE("拒绝");
    private String text;

    OrderStatus(String text) {
        this.text = text;
    }
}

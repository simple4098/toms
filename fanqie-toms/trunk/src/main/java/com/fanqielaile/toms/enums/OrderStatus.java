package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 订单状态
 */
public enum OrderStatus {
    ACCEPT("自动接受"),
    DEAL("已处理"),
    NOT_DEAL("待处理"),
    CONFIM_AND_ORDER("确认并下单"),
    CONFIM_NO_ORDER("已确认但不下单"),
    CANCEL_ORDER("已取消"),
    HAND_REFUSE("直接拒绝"),
    PAY_BACK("退款申请中"),
    DEALING("处理中"),
    REFUSE("自动拒绝"),
	CANCEL_APPLY("取消申请中");
    private String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

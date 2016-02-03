package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2016/1/29.
 */
public enum OtaOrderStatus {
    Error("未找到"),
    WAIT_BUYER_PAY("预订中/等待买家付款"), WAIT_SELLER_SEND_GOODS("预订中/等待卖家发货(确认)"), TRADE_CLOSED("结束/预订失败/交易关闭"), TRADE_FINISHED("结束/交易成功,"),
    TRADE_NO_CREATE_PAY("结束/预订失败/没有创建支付宝交易"), TRADE_CLOSED_BY_TAOBAO("结束/预订失败/预订被卖家关闭"), TRADE_SUCCESS("交易中/预订成功/卖家已确认"),
    TRADE_CHECKIN("交易中/预定成功/买家入住"), TRADE_CHECKOUT("交易中/预定成功/买家离店"), TRADE_SETTLEING("交易中/预定成功/结账中"), TRADE_SETTLE_SUCCESS("结束/预定成功/结账成功");

    private String text;

    OtaOrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

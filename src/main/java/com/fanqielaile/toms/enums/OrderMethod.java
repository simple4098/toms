package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/19.
 * 接口请求的方法：创建，取消，查询订单
 */
public enum OrderMethod {
    BookRQ("创建订单"),
    CancelRQ("取消订单"),
    QueryStatusRQ("查询订单状态");
    private String text;

    OrderMethod(String text) {
        this.text = text;
    }
}

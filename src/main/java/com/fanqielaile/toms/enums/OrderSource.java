package com.fanqielaile.toms.enums;

/**
 * Created by Administrator on 2016/5/30.
 * 订单来源
 */
public enum OrderSource {
    SYSTEM("系统"),
    HAND("手动");
    private String text;

    OrderSource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

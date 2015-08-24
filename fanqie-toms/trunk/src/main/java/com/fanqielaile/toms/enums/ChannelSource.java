package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 渠道来源
 */
public enum ChannelSource {
    TAOBAO("淘宝"),
    HAND_ORDER("手动下单");
    private String text;

    ChannelSource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

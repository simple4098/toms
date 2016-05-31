package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/6/18.
 * 渠道来源
 */
public enum ChannelSource {
    TAOBAO("淘宝"),
    FC("天下房仓"),
    HAND_ORDER("其他渠道"), XC("携程"), ZH("众荟"), QUNAR("去哪儿");
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

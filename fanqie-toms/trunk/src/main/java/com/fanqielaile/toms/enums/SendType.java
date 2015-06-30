package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/5/15.
 */
public enum SendType {
    ALL("短信和系统弹窗"),
    MESSAGE("短信通知"),
    POPUP("系统弹窗");
    private String text;

    SendType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

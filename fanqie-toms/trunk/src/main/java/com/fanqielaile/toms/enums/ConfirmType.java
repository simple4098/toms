package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/9/1.
 */
public enum ConfirmType {
    ON_LINE("在线确认"),
    ON_EMAIL("传真确认"),
    OTHER("其他方式");

    ConfirmType(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

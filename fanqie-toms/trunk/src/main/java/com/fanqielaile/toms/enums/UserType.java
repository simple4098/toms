package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/5/11.
 */
public enum  UserType {
    ADMIN("超级管理员"),
    PUBLIC("普通员工");

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    UserType(String text) {
        this.text = text;
    }
}

package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2015/9/1.
 */
public enum FcBreakFastType {
    CHINA("中餐"),
    WEST("西餐"),
    AUTO("自主");

    FcBreakFastType(String text) {
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

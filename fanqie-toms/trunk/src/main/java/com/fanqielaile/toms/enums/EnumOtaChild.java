package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2016/3/17.
 */
public enum EnumOtaChild {

    MG("芒果网"),
    FANGCANG("途牛"),
    YX("悦享"),
    NR("云南纳若"),
    LY("旅游百事通");

    private String text;

    EnumOtaChild(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

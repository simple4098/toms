package com.fanqielaile.toms.enums;

public enum TBType {
    //todo
    NOT_HAVE("全新自动上线去啊后台"),
    NULL("空值");
   /* HAVE("匹配去啊后台已有酒店");*/
    private String text;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    TBType(String text) {
        this.text = text;
    }
}

package com.fanqielaile.toms.enums;

/**
 * DESC :退订类型
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public enum  CancelType {
    ARBITRARY_RETREAT("任意退"),NOT_RETREAT("不能退"),COUNTER_FEE("收手续费"),FREE_OR_CHARGE("可以免费退"),;
    private String value;

    CancelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}



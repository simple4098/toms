package com.fanqielaile.toms.enums;

/**
 * DESC : 含早类型 0：不含早1：含单早2：含双早N：含N早（1-99可选）
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public enum BreakfastType {
    ZERO("不含早"),ONE("含单早"),TWO("含双早"),N("含N早(1-99可选)");

    private String value;

    BreakfastType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.fanqielaile.toms.enums;

/**
 * DESC : 房仓匹配
 * @author : 番茄木-ZLin
 * @data : 2015/9/14
 * @version: v1.0.0
 */
public enum InnStatus {
    MATCH("匹配成功"),NOT_MATCH("未匹配");

    private String value;
    InnStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

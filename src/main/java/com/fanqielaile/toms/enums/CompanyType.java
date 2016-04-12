package com.fanqielaile.toms.enums;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/11/19
 * @version: v1.0.0
 */
public enum CompanyType {
    OPEN("线下专有"),SALE("番茄代销"),CREDIT("信用住");
    private String desc;

    CompanyType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

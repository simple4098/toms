package com.fanqielaile.toms.enums;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public enum BedType {
    BigBed("1000000","大床"),DoubleBed("2000000","双床"),onlyBed("A000000","单人床"),ThreeBed("3000000","3床"),FourBed("4000000","4床");
    private String value;
    private String desc;

    BedType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

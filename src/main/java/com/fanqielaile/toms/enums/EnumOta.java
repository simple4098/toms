package com.fanqielaile.toms.enums;

/**
 * Created by lei on 2015/12/3.
 */
public enum EnumOta {
    xz(101),//小站
    dx(102),//代销
    xyz(106),//信用住
    qunar_direct(107),//去哪儿直连
    kf(111),//开放平台
    wg(1),//用于判断是否外挂业务
    qunar_conn(107),//去哪儿直连
    ctrip_conn(108),//携程直连
    qunar(1),//去哪儿
    ctrip(2),//携程
    elong(3),//艺龙
    ctrip_homestay(936) //携程民宿

    ;

    private int value;

    EnumOta(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

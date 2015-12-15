package com.fanqielaile.toms.enums;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/12/9
 * @version: v1.0.0
 */
public enum TimerRateType {
    NOT_HOVE_ROUSE("无房"),NEW("新增房型");
    private String desc;

    TimerRateType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

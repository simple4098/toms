package com.fanqielaile.toms.enums;

/**
 * DESC : 日志类型
 * @author : 番茄木-ZLin
 * @data : 2016/1/28
 * @version: v1.0.0
 */
public enum LogDec {
    INN_PUSH(7,701,"推送客栈信息"),RoomType_PHSH(7,702,"推送房型信息"),
    RoomType_PHSH_PRICE(7,703,"推送房型价格"),MT_RoomType_Price(7,704,"手动改房型价格"),
    XJ_INN(7,705,"下架客栈"),SJ_RoomType(7,706,"上架房型"),XJ_RoomType(7,707,"下架房型"),Order(7,708,"订单");
    //父id
    private Integer pId;
    //子id
    private Integer logTypeId;
    //描述
    private String value;

    LogDec(Integer pId, Integer logTypeId, String value) {
        this.pId = pId;
        this.logTypeId = logTypeId;
        this.value = value;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getLogTypeId() {
        return logTypeId;
    }

    public void setLogTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

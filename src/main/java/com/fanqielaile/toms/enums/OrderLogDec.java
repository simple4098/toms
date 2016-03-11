package com.fanqielaile.toms.enums;

/**
 * Created by wangdayin on 2016/3/1.
 */
public enum OrderLogDec {
    ADD_ORDER(2, 21, "下单到toms"),
    CANCEL_ORDER(2, 25, "删除"),
    CREATE_ORDER_TO_OMS(2, 26, "下单到oms"),
    REQUEST_PARAM(2, 27, "请求参数"),
    RESPONSE_RETURN(2, 28, "返回值"),
    SEARCH_ORDER(2, 29, "查询订单"), CHECK_ORDER(2, 30, "试订单"), PAY_BACK(2, 31, "申请退款");

    //父id
    private Integer pId;
    //子id
    private Integer logTypeId;
    //描述
    private String value;

    OrderLogDec(Integer pId, Integer logTypeId, String value) {
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

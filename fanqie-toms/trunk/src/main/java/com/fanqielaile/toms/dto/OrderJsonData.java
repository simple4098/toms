package com.fanqielaile.toms.dto;

/**
 * Created by wangdayin on 2016/7/7.
 * 下单到oms，jsonData字段
 */
public class OrderJsonData {
    private String pmsChannelName;
    private String myselfChannelName;
    private String myselfChannelCode;

    public OrderJsonData() {
    }

    public OrderJsonData(String pmsChannelName, String myselfChannelName, String myselfChannelCode) {
        this.pmsChannelName = pmsChannelName;
        this.myselfChannelName = myselfChannelName;
        this.myselfChannelCode = myselfChannelCode;
    }

    public String getPmsChannelName() {
        return pmsChannelName;
    }

    public void setPmsChannelName(String pmsChannelName) {
        this.pmsChannelName = pmsChannelName;
    }

    public String getMyselfChannelName() {
        return myselfChannelName;
    }

    public void setMyselfChannelName(String myselfChannelName) {
        this.myselfChannelName = myselfChannelName;
    }

    public String getMyselfChannelCode() {
        return myselfChannelCode;
    }

    public void setMyselfChannelCode(String myselfChannelCode) {
        this.myselfChannelCode = myselfChannelCode;
    }
}

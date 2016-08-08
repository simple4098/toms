package com.fanqielaile.toms.dto;

/**
 * Created by wangdayin on 2016/7/7.
 * 下单到oms，jsonData字段
 */
public class OrderJsonData {
    private String pmsChannelName;//pms展示的渠道名称
    private String myselfChannelName;//自定义渠道名称
    private String myselfChannelCode;//自定义渠道code
    private String orderChannelName;//渠道商
    private String orderChannelCode;//渠道商code
    private String accessName;//通道商
    private String accessCode;//通道商code

    public OrderJsonData() {
    }

    public OrderJsonData(String pmsChannelName, String myselfChannelName, String myselfChannelCode, String orderChannelName, String orderChannelCode, String accessName, String accessCode) {
        this.pmsChannelName = pmsChannelName;
        this.myselfChannelName = myselfChannelName;
        this.myselfChannelCode = myselfChannelCode;
        this.orderChannelName = orderChannelName;
        this.orderChannelCode = orderChannelCode;
        this.accessName = accessName;
        this.accessCode = accessCode;
    }

    public OrderJsonData(String pmsChannelName, String myselfChannelName, String myselfChannelCode) {
        this.pmsChannelName = pmsChannelName;
        this.myselfChannelName = myselfChannelName;
        this.myselfChannelCode = myselfChannelCode;
    }

    public String getOrderChannelName() {
        return orderChannelName;
    }

    public void setOrderChannelName(String orderChannelName) {
        this.orderChannelName = orderChannelName;
    }

    public String getOrderChannelCode() {
        return orderChannelCode;
    }

    public void setOrderChannelCode(String orderChannelCode) {
        this.orderChannelCode = orderChannelCode;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
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

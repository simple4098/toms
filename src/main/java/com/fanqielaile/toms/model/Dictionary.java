package com.fanqielaile.toms.model;

/**
 * Created by wangdayin on 2015/6/24.
 * 字典表
 */
public class Dictionary {
    private String id;
    //描述
    private String desc;
    //请求的url
    private String url;
    private String type;
    //值
    private String value;
    //签名的名称
    private String vName;
    //签名的密码
    private String vPWD;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvPWD() {
        return vPWD;
    }

    public void setvPWD(String vPWD) {
        this.vPWD = vPWD;
    }
}

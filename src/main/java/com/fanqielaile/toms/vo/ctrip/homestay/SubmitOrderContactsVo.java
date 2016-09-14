package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/6 10:45
 * Version: 1.0
 * Description: 阐述
 * contacts		object
 * name	string
 * mobile	string
 */
public class SubmitOrderContactsVo {
    private String name;
    private String mobile;

    public SubmitOrderContactsVo() {
        super();
    }

    public SubmitOrderContactsVo(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

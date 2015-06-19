package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * DESC : 第三方系统中开通的渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInfo extends Domain {

    //渠道名称
    private String otaInfo;

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }
}

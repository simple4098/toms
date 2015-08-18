package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;

/**
 * DESC : 行政区
 * @author : 番茄木-ZLin
 * @data : 2015/8/12
 * @version: v1.0.0
 */
public class FcArea extends Domain {
    //房仓城市code
    private String cityCode;
    //房仓区域名称
    private String  areaName;
    //房仓区域code
    private String areaCode;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}

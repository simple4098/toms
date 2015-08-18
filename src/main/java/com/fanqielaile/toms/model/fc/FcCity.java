package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;

/**
 * DESC :房仓城市表
 * @author : 番茄木-ZLin
 * @data : 2015/8/12
 * @version: v1.0.0
 */
public class FcCity extends Domain {
    //省份code
    private String provinceCode;
    //城市名称
    private String cityName;
    //城市code
    private String cityCode;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}

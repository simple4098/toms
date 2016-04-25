package com.fanqielaile.toms.model.qunar;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2016/4/15.
 * 去哪儿酒店城市信息
 */
public class QunarCityInfo extends Domain {

    //城市code
    private String cityCode;
    //城市类型
    private String cityType;
    //城市名称
    private String name;
    //英文名称
    private String enName;
    //地址
    private String areaPath;
    //是否为最后城市
    private String isRootCity;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }

    public String getIsRootCity() {
        return isRootCity;
    }

    public void setIsRootCity(String isRootCity) {
        this.isRootCity = isRootCity;
    }
}

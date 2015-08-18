package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;

/**
 * DESC : 房仓省份
 * @author : 番茄木-ZLin
 * @data : 2015/8/12
 * @version: v1.0.0
 */
public class FcProvince extends Domain {
    //省份code
    private String provinceCode;
    //省份名称
    private String provinceName;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}

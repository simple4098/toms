package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OtaType;

/**
 * DESC : 第三方系统中开通的渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInfo extends Domain {

    //渠道名称
    private String otaInfo;
    private String companyId;
    private OtaType otaType;
  /*  private String appKey;
    private String appSecret;
    private String sessionKey;*/
    //排序
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }

    public OtaType getOtaType() {
        return otaType;
    }

    public void setOtaType(OtaType otaType) {
        this.otaType = otaType;
    }


}

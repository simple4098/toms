package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.enums.BangType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.OtaInfo;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/6
 * @version: v1.0.0
 */
public class OtaInfoDto extends OtaInfo {
    //渠道id
    private Integer otaId;
    //oms给第三公司提供userAccount
    private String userAccount;
    //oms给第三公司提供userPassword
    private String userPassword;
    //企业唯一码
    private String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }
}

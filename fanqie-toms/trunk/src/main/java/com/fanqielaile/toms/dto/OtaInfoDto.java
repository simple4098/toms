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
    private String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

}

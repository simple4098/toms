package com.fanqielaile.toms.dto;

import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/11/6
 * @version: v1.0.0
 */
public class ParamJson {

    private String companyCode;

    private Map commission;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Map getCommission() {
        return commission;
    }

    public void setCommission(Map commission) {
        this.commission = commission;
    }
}

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/25
 * @version: v1.0.0
 */
public class OtherConsumerFunction extends Domain {
    //公司id
    private String companyId;
    //是否开启
    private Boolean status;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

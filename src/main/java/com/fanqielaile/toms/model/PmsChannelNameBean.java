package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2016/7/6.
 */
public class PmsChannelNameBean extends Domain {

    private String companyId;
    private String pmsChannelName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPmsChannelName() {
        return pmsChannelName;
    }

    public void setPmsChannelName(String pmsChannelName) {
        this.pmsChannelName = pmsChannelName;
    }
}

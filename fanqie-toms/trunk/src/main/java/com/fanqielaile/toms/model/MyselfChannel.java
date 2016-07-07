package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2016/7/5.
 */
public class MyselfChannel extends Domain {

    private String companyId;
    private String channelName;
    private String channelCode;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}

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
    //是否开启其他消费
    private Boolean status;
    //是否开启自定义渠道
    private Boolean myselfChannelStatus;
    //是否开启pms渠道名称
    private Boolean pmsChannelNameStatus;

    public Boolean getPmsChannelNameStatus() {
        return pmsChannelNameStatus;
    }

    public void setPmsChannelNameStatus(Boolean pmsChannelNameStatus) {
        this.pmsChannelNameStatus = pmsChannelNameStatus;
    }

    public Boolean getMyselfChannelStatus() {
        return myselfChannelStatus;
    }

    public void setMyselfChannelStatus(Boolean myselfChannelStatus) {
        this.myselfChannelStatus = myselfChannelStatus;
    }

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

package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.PmsChannelNameBean;
import com.fanqielaile.toms.model.UserInfo;

/**
 * Created by wangdayin on 2016/7/6.
 */
public interface IPmsChannelNameService {
    /**
     * 查询pms渠道名
     * @param currentUser
     * @return
     */
    PmsChannelNameBean findPmsChannelNameByCompanyId(UserInfo currentUser);

    /**
     * 创建pms渠道名
     * @param pmsChannelNameBean
     * @param currentUser
     */
    void createPmsChannelName(PmsChannelNameBean pmsChannelNameBean, UserInfo currentUser);
}

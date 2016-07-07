package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.MyselfChannel;
import com.fanqielaile.toms.model.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/7/5.
 */
public interface IMyselfChannelService {
    /**
     * 创建自定义渠道
     * @param myselfChannel
     * @param currentUser
     */
    void createMyselfChannel(MyselfChannel myselfChannel, UserInfo currentUser);

    /**
     * 根据名字查询自定义渠道
     * @param channelName
     * @return
     */
    MyselfChannel findMyselfChannelByName(String channelName);

    /**
     * 查询当前登录用户公司创建的自定义渠道
     * @param currentUser
     * @return
     */
    List<MyselfChannel> findMyselfChannelList(UserInfo currentUser);

    /**
     * 修改自定义渠道名称
     * @param id
     * @param channelName
     * @param currentUser
     * @return
     */
    Map<String,Object> modifyMyselfChannel(String id, String channelName, UserInfo currentUser);

    /**
     * 删除自定义渠道
     * @param id
     */
    void removeMyselfChannelById(String id);

    /**
     * 更新公司开关自定义渠道状态
     * @param currentUser
     * @param status
     */
    void modifyCompanyChannelStatus(UserInfo currentUser,Boolean status);

    /**
     * 更新公司pms渠道状态
     * @param currentUser
     * @param status
     */
    void modifyPmsCompanyChannelStatus(UserInfo currentUser, Boolean status);
}

package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.MyselfChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2016/7/5.
 */
public interface MyselfChannelDao {
    /**
     * 新增自定义渠道
     * @param myselfChannel
     */
    void insertIntoMyselfChannel(MyselfChannel myselfChannel);

    /**
     * 修改自定义渠道
     * @param myselfChannel
     */
    void updateMySelfChannel(MyselfChannel myselfChannel);

    /**
     * 根据id删除自定义渠道
     * @param id
     */
    void deletedMySelfChannel(String id);

    /**
     * 根据公司id查询公司自定义渠道
     * @param companyId
     * @return
     */
    List<MyselfChannel> selectMyselfChannelByCompanyId(@Param("companyId")String companyId);

    /**
     * 根据id查询自定义渠道
     * @param id
     * @return
     */
    MyselfChannel selectMyselfChannelById(String  id);

    /**
     * 根据渠道code查询自定义渠道
     * @param channelCode
     * @return
     */
    MyselfChannel selectMyselfChannelCode(@Param("channelCode")String channelCode);

    /**
     * 根据名字查询自定义渠道
     * @param channelName
     * @return
     */
    MyselfChannel selectMyselfChannelByChannelName(@Param("channelName")String channelName);

    /**
     * 更新公司自定义渠道开关状态
     * @param companyId
     */
    void updateCompanyChannelStatus(@Param("companyId")String companyId,@Param("status")Boolean status);

    /**
     * 更新公司pms渠道名状态
     * @param companyId
     * @param status
     */
    void updatePmsCompanyChannelStatus(@Param("companyId")String companyId,@Param("status")Boolean status);
}

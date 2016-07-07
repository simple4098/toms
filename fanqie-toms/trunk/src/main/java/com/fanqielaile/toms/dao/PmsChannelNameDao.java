package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.PmsChannelNameBean;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangdayin on 2016/7/6.
 */
public interface PmsChannelNameDao {
    /**
     * 插入pms渠道名
     * @param pmsChannelNameBean
     */
    void insertIntoPmsChannelName(PmsChannelNameBean pmsChannelNameBean);

    /**
     * 根据公司id查询pms渠道名
     * @param companyId
     * @return
     */
    PmsChannelNameBean selectPmsChannelNameByCompanyId(@Param("companyId")String companyId);
}

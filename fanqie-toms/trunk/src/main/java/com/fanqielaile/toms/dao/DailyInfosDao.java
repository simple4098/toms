package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/6/19.
 */
public interface DailyInfosDao {
    /**
     * 创建每日价格信息
     *
     * @param order
     */
    void insertDailyInfos(Order order);

    /**
     * 根据订单ID查询每日房价信息
     *
     * @param orderId
     * @return
     */
    List<DailyInfos> selectDailyInfoByOrderId(@Param("orderId") String orderId);
}

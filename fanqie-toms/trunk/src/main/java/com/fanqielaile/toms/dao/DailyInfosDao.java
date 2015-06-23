package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Order;

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
}

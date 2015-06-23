package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Order;

/**
 * Created by wangdayin on 2015/6/19.
 */
public interface OrderDao {
    /**
     * 创建订单
     *
     * @param order
     */
    void insertOrder(Order order);
}

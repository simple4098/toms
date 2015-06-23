package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Order;

/**
 * Created by wangdayin on 2015/6/19.
 */
public interface OrderGuestsDao {
    /**
     * 创建入住人信息
     *
     * @param order
     */
    void insertOrderGuests(Order order);
}

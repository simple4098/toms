package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据订单ID查询入住人信息
     *
     * @param orderId
     * @return
     */
    List<OrderGuests> selectOrderGuestByOrderId(@Param("orderId") String orderId);
}

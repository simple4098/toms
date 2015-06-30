package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.Order;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 取消订单
     *
     * @param orderId
     * @param channelSource
     * @return
     */
    Order selectOrderByIdAndChannelSource(@Param("orderId") String orderId, @Param("channelSource") ChannelSource channelSource);

    /**
     * 取消订单，更新订单的状态和原因
     *
     * @param order
     */
    void updateOrderStatusAndReason(Order order);

    /**
     * 新增订单失败，更新订单状态和付款状态
     *
     * @param order
     */
    void updateOrderStatusAndFeeStatus(Order order);
}

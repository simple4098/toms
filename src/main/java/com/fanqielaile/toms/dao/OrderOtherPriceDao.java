package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.OrderOtherPrice;

/**
 * Created by wangdayin on 2016/3/31.
 */
public interface OrderOtherPriceDao {
    /**
     * 创建订单其他费用
     *
     * @param orderOtherPrice
     */
    void insertOrderOtherPrice(OrderOtherPrice orderOtherPrice);
}

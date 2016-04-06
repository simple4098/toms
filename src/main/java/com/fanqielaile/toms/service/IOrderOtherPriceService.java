package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.OrderOtherPrice;

/**
 * Created by wangdayin on 2016/3/31.
 */
public interface IOrderOtherPriceService {
    /**
     * 创建订单其他费用
     *
     * @param orderOtherPrice
     */
    void createOrderOtherPrice(OrderOtherPrice orderOtherPrice);
}

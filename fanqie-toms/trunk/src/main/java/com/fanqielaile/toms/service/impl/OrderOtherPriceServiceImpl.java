package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.OrderOtherPriceDao;
import com.fanqielaile.toms.model.OrderOtherPrice;
import com.fanqielaile.toms.service.IOrderOtherPriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2016/3/31.
 */
@Service
public class OrderOtherPriceServiceImpl implements IOrderOtherPriceService {
    @Resource
    private OrderOtherPriceDao orderOtherPriceDao;

    @Override
    public void createOrderOtherPrice(OrderOtherPrice orderOtherPrice) {
        this.orderOtherPriceDao.insertOrderOtherPrice(orderOtherPrice);
    }
}

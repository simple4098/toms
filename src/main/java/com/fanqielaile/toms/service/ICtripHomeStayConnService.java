package com.fanqielaile.toms.service;

import com.fanqielaile.toms.vo.ctrip.homestay.*;

import java.util.Map;

/**
 * Create by jame
 * Date: 2016/9/6 10:26
 * Version: 1.0
 * Description: 阐述
 */
public interface ICtripHomeStayConnService {

    SubmitOrderReturnVo submitOrder(Map map);

    GetOrderReturnVo getOrder(Map map);

    CancelOrderReturnVo cancelOrder(CancelOrderRequestVo cancelOrderRequestVo);
}

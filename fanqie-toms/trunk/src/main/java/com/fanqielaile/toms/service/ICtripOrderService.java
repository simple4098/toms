package com.fanqielaile.toms.service;

import com.fanqie.bean.order.CtripCancelHotelOrderResponse;
import com.fanqie.bean.order.CtripCheckRoomAvailResponse;
import com.fanqie.bean.order.CtripGetOrderStatusResponse;
import com.fanqie.bean.order.CtripNewHotelOrderResponse;

/**
 * Created by Administrator on 2015/12/21.
 */
public interface ICtripOrderService {
    /**
     * 验证用户信息是否正确
     *
     * @param xml
     * @return
     * @throws Exception
     */
    boolean checkCtripUserPassword(String xml) throws Exception;

    /**
     * 携程试订单
     *
     * @param xml
     * @return
     */
    CtripCheckRoomAvailResponse dealCtripCheckRoomAvail(String xml) throws Exception;

    /**
     * 携程取消订单
     *
     * @param xml
     * @return
     * @throws Exception
     */
    CtripCancelHotelOrderResponse cancelOrderMethod(String xml) throws Exception;

    /**
     * 携程获取订单状态
     *
     * @param xml
     * @return
     */
    CtripGetOrderStatusResponse getOrderStatus(String xml) throws Exception;

    /**
     * 携程新增订单
     *
     * @param xml
     * @return
     */
    CtripNewHotelOrderResponse addOrder(String xml) throws Exception;
}

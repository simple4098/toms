package com.fanqielaile.toms.service;

import com.fanqie.qunar.response.BookingResponse;
import com.fanqie.qunar.response.QunarCancelOrderResponse;
import com.fanqie.qunar.response.QunarGetRoomTypeInfoResponse;
import com.fanqie.qunar.response.QunarWrapperOrderQueryResponse;

/**
 * Created by wangdayin on 2016/4/15.
 */
public interface IQunarOrderService {
    /**
     * 去哪儿获取房型信息
     *
     * @param xml
     * @return
     */
    QunarGetRoomTypeInfoResponse findRoomTypeInfo(String xml);

    /**
     * 去哪儿创建订单
     *
     * @param xml
     * @return
     */
    BookingResponse createOrderByQunar(String xml);

    /**
     * 去哪儿取消订单
     *
     * @param xml
     * @return
     */
    QunarCancelOrderResponse cancelOrderMethod(String xml) throws Exception;

    /**
     * 去哪儿查询订单
     *
     * @param xml
     * @return
     */
    QunarWrapperOrderQueryResponse queryOrderStatus(String xml) throws Exception;
}

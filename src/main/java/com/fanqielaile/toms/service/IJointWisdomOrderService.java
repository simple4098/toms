package com.fanqielaile.toms.service;

import java.util.Map;

/**
 * Created by wangdayin on 2016/1/11.
 * 众荟订单service
 */
public interface IJointWisdomOrderService {
    /**
     * 众荟试订单
     *
     * @param xml
     * @return
     * @throws Exception
     */
    String dealAvailCheckOrder(String xml) throws Exception;

    /**
     * 众荟新增订单
     *
     * @param xml
     * @return
     */
    String dealAddOrder(String xml) throws Exception;

    /**
     * 众荟取消订单
     *
     * @param xml
     * @return
     */
    String dealCancelOrder(String xml) throws Exception;
}

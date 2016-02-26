package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.model.ExceptionOrder;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.support.util.JsonModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/28.
 */
public interface IExceptionOrderService {
    void createExceptionOrder(Order order);

    void modifyExceptionOrder(ExceptionOrder exceptionOrder);

    /**
     * 分页查询异常订单列表
     *
     * @param pageBounds
     * @return
     */
    List<Order> findAllExceptionOrder(PageBounds pageBounds) throws Exception;

    /**
     * 删除异常订单
     *
     * @param order
     */
    void deleteExceptionOrder(Order order);

    /**
     * 关闭订单
     *
     * @param orderParamDto
     * @return
     */
    JsonModel dealCloseOrder(OrderParamDto orderParamDto) throws Exception;

    /**
     * 获取oms订单状态
     *
     * @param orderParamDto
     * @return
     */
    Map<String, Object> findOmsOrderStatus(OrderParamDto orderParamDto) throws IOException;

    /**
     * 获取pms订单状态
     *
     * @param orderParamDto
     * @return
     */
    Map<String, Object> finPmsOrderStatus(OrderParamDto orderParamDto) throws Exception;
}

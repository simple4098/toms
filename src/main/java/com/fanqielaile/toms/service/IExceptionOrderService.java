package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.ExceptionOrder;
import com.fanqielaile.toms.model.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

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
}

package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.ExceptionOrder;
import com.fanqielaile.toms.model.Order;

/**
 * Created by wangdayin on 2016/1/28.
 */
public interface ExceptionOrderDao {
    void insertExceptionOrder(Order order);

    void updateExceptionOrder(ExceptionOrder exceptionOrder);

    void deleteExceptionOrder(Order order);

    void insertExceptionOrderByException(ExceptionOrder exceptionOrder);
}

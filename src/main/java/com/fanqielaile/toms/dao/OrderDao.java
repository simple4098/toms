package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/6/19.
 */
public interface OrderDao {
    /**
     * 创建订单
     *
     * @param order
     */
    void insertOrder(Order order);

    /**
     * 取消订单
     *
     * @param orderId
     * @param channelSource
     * @return
     */
    Order selectOrderByIdAndChannelSource(@Param("orderId") String orderId, @Param("channelSource") ChannelSource channelSource);

    /**
     * 取消订单，更新订单的状态和原因
     *
     * @param order
     */
    void updateOrderStatusAndReason(Order order);

    /**
     * 新增订单失败，更新订单状态和付款状态
     *
     * @param order
     */
    void updateOrderStatusAndFeeStatus(Order order);

    /**
     * 分页查询订单信息
     *
     * @param companyId
     * @param pageBounds
     * @param orderParamDto
     * @return
     */
    List<OrderParamDto> selectOrderByPage(@Param("companyId") String companyId, PageBounds pageBounds, @Param("order") OrderParamDto orderParamDto);

    /**
     * 查询订单信息
     *
     * @param companyId
     * @param orderParamDto
     * @return
     */
    List<OrderParamDto> selectOrders(@Param("companyId") String companyId, @Param("order") OrderParamDto orderParamDto);

    /**
     * 根据订单ID查询订单信息
     *
     * @param id
     * @return
     */
    OrderParamDto selectOrderById(@Param("id") String id);

    /**
     * 查询渠道来源
     *
     * @param companyId
     * @return
     */
    List<Order> selectOrderChancelSource(@Param("companyId") String companyId);

    /**
     * 根据渠道订单号和渠道查询订单
     *
     * @param order
     * @return
     */
    Order selectOrderByChannelOrderCodeAndSource(Order order);

    /**
     * 根据oms订单号和渠道订单号查询订单
     *
     * @param channelOrderCode
     * @return
     */
    Order selectOrderByOmsOrderCodeAndChannelSourceCode(@Param("channelOrderCode") String channelOrderCode, @Param("omsOrderCode") String omsOrderCode);

    /**
     * 不分页查询
     *
     * @param orderParamDto
     * @return
     */
    List<OrderParamDto> selectOrderByNoPage(@Param("order") OrderParamDto orderParamDto);

    List<Order> selectExceptionOrderList(@Param("order") OrderParamDto orderParamDto);

    /**
     * 分页查询异常订单列表
     *
     * @param pageBounds
     * @return
     */
    List<Order> selectAllExceptionOrder(PageBounds pageBounds);
}

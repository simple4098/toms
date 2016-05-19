package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.xl.CustomerAnalysisDto;
import com.fanqielaile.toms.dto.xl.CustomerParamDto;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangdayin on 2015/6/19.
 */
public interface OrderGuestsDao {
    /**
     * 创建入住人信息
     *
     * @param order
     */
    void insertOrderGuests(Order order);
    /**
     * 更新入住人归属地信息
     *
     * @param order
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void updateOrderGuests(List<OrderGuests> guests);

    /**
     * 根据订单ID查询入住人信息
     *
     * @param orderId
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<OrderGuests> selectOrderGuestByOrderId(@Param("orderId") String orderId);
    /**
     * 统计省份客户数量
     * @param currentUser 当前登录用户
     * @param customerParamDto 查询参数
     * @param pageBounds
     * @return
     */
    List<CustomerAnalysisDto> selectProvinceGuestNumList(@Param("customerParamDto") CustomerParamDto customerParamDto,@Param("currentUser") UserInfo currentUser,
			PageBounds pageBounds);
	/**
     * 统计城市客户数量
     * @param currentUser 当前登录用户
     * @param customerParamDto 查询参数
     * @param pageBounds
     * @return
     */
	List<CustomerAnalysisDto> selectCityGuestNumList(@Param("customerParamDto") CustomerParamDto customerParamDto,@Param("currentUser") UserInfo currentUser,
			PageBounds pageBounds);
	/**
     * 统计总客户数量
     * @param currentUser 当前登录用户
     * @param customerParamDto 查询参数
     * @return
     */
	Integer getTotalGuestCount(@Param("customerParamDto")CustomerParamDto customerParamDto,@Param("currentUser")  UserInfo currentUser);
}

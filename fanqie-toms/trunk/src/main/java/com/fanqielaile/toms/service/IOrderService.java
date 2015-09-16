package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.taobao.api.ApiException;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;

import java.util.List;
import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
public interface IOrderService {


    /**
     * 订单来源、概况
     * @param paramDto
     * @param userInfo
     * @return
     * @throws Exception
     */
    Map<String,Object> findOrderSourceDetail(ParamDto paramDto,UserInfo userInfo) throws Exception;

    /**
     * 创建订单
     *
     * @param xmlStr
     */
    Order addOrder(String xmlStr, ChannelSource channelSource) throws Exception;

    /**
     * 取消订单
     *
     * @param xmlStr
     * @param channelSource
     * @throws Exception
     */
    JsonModel cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception;

    /**
     * 付款成功回调
     *
     * @param xmlStr
     * @param channelSource
     * @return
     * @throws Exception
     */
    JsonModel paymentSuccessCallBack(String xmlStr, ChannelSource channelSource) throws Exception;

    /**
     * 查询订单状态
     *
     * @param xmlStr
     * @param channelSource
     * @return
     */
    Map<String, String> findOrderStatus(String xmlStr, ChannelSource channelSource) throws Exception;

    /**
     * 退款成功回调
     *
     * @param xmlStr
     * @param taobao
     * @return
     */
    Map<String, String> dealPayBackMethod(String xmlStr, ChannelSource taobao) throws Exception;


    /**
     * 查询订单信息
     *
     * @param companyId
     * @param pageBounds
     * @param orderParamDto
     * @return
     */
    List<OrderParamDto> findOrderByPage(String companyId, PageBounds pageBounds, OrderParamDto orderParamDto);

    /**
     * 查询所有的订单信息
     *
     * @param companyId
     * @param orderParamDto
     * @return
     */
    OrderParamDto findOrders(String companyId, OrderParamDto orderParamDto);

    /**
     * 根据订单id查询订单
     *
     * @param id
     * @return
     */
    OrderParamDto findOrderById(String id);

    /**
     * 手动确认并下单
     *
     * @param order
     * @param currentUser
     */
    JsonModel confirmOrder(OrderParamDto order, UserInfo currentUser) throws Exception;

    /**
     * 直接拒绝订单
     *
     * @param order
     * @param currentUser
     */
    JsonModel refuesOrder(OrderParamDto order, UserInfo currentUser) throws ApiException;

    /**
     * 确认但不执行下单
     *
     * @param order
     * @param currentUser
     */
    JsonModel confirmNoOrder(OrderParamDto order, UserInfo currentUser) throws ApiException;

    /**
     * 手动下单
     *
     * @param order
     * @return
     */
    Map<String, Object> dealHandMakeOrder(Order order, UserInfo userInfo) throws Exception;

    /**
     * 查询渠道来源
     *
     * @param companyId
     * @return
     */
    List<Order> findOrderChancelSource(String companyId);

    /**
     * 查询手动下单时可以选择的房型信息
     *
     * @param order
     * @return
     */
    List<RoomTypeInfoDto> findHandOrderRoomType(Order order, UserInfo userInfo) throws Exception;

    /**
     * 同意退款
     *
     * @param order
     * @param currentUser
     * @return
     */
    JsonModel agreePayBackOrder(OrderParamDto order, UserInfo currentUser) throws Exception;

    /**
     * 拒绝退款
     *
     * @param order
     * @param currentUser
     * @return
     */
    JsonModel refusePayBackOrder(OrderParamDto order, UserInfo currentUser);

    /**
     * 天下房仓创建订单
     *
     * @param xml
     * @return
     */
    Map<String, Object> createFcHotelOrder(String xml) throws Exception;

    /**
     * 天下房仓取消订单
     *
     * @param xml
     * @return
     */
    CancelHotelOrderResponse cancelFcHotelOrder(String xml) throws Exception;

    /**
     * 天下房仓获取订单状态
     *
     * @param xml
     * @return
     */
    GetOrderStatusResponse getFcOrderStatus(String xml) throws Exception;

    /**
     * 天下房仓试订单处理方法
     *
     * @param xml
     * @return
     */
    CheckRoomAvailResponse checkRoomAvail(String xml);

    /**
     * 获取增量天下房仓接口
     */
    UploadStatus getFcAddHotelInfo() throws DocumentException;
}

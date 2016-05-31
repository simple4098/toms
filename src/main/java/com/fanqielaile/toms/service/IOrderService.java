package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderOtherPrice;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.taobao.api.ApiException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
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
    Map<String, Object> addOrder(String xmlStr, ChannelSource channelSource) throws Exception;

    /**
     * 取消订单
     *
     * @param xmlStr
     * @param channelSource
     * @throws Exception
     */
    Map<String, Object> cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception;

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
    OrderParamDto findOrderById(String id) throws IOException;

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
    JsonModel refusePayBackOrder(OrderParamDto order, UserInfo currentUser) throws IOException;

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
    CheckRoomAvailResponse checkRoomAvail(String xml) throws IOException;

    /**
     * 获取增量天下房仓接口
     */
    UploadStatus getFcAddHotelInfo() throws DocumentException;

    /**
     * 取消手动下单
     *
     * @param orderParamDto
     * @return
     */
    JsonModel cancelHandOrder(OrderParamDto orderParamDto) throws Exception;

    /**
     * 同步oms订单状态
     *
     * @param order
     */
    void SynchronousOmsOrderStatus(Order order);

    /**
     * 推送订单状态
     *
     * @param pushXml
     */
    void pushOrderStatusMethod(String pushXml) throws Exception;

    /**
     * 订单导出
     *
     * @param currentUser
     * @param orderParamDto
     * @param response
     * @param selectedOperators 
     * @param operatorsJson 
     * @param selectStatusString 
     * @throws Exception
     */
    void dealOrderExport(UserInfo currentUser, OrderParamDto orderParamDto, HttpServletResponse response, String operatorsJson, String selectedOperators, String selectStatusString) throws Exception;

    /**
     * 取消订单
     *
     * @param order
     * @return
     * @throws Exception
     */
    JsonModel cancelOrderMethod(Order order) throws Exception;

    /**
     * 获取订单状态
     *
     * @param order
     * @return
     * @throws Exception
     */
    String getOrderStatusMethod(Order order) throws Exception;

    /**
     * 创建toms本地订单
     *
     * @param channelSource
     * @param order
     * @throws IOException
     */
    JsonModel createOrderMethod(ChannelSource channelSource, Order order) throws IOException;

    /**
     * 订单付款
     *
     * @param order
     * @param userInfo
     * @param otaType
     * @return
     * @throws Exception
     */
    JsonModel payBackDealMethod(Order order, UserInfo userInfo, String otaType) throws Exception;

    /**
     * 查询异常订单
     *
     * @param map
     * @return
     */
    List<Order> findExceptionOrderList(Map<String, String> map);

    /**
     * 下单到oms
     *
     * @param orderParamDto
     * @param currentUser
     * @return
     */
    JsonModel createOrderOmsMethod(OrderParamDto orderParamDto, UserInfo currentUser) throws Exception;

    /**
     * 取消订单到oms
     *
     * @param orderParamDto
     * @param currentUser
     * @return
     */
    JsonModel cancelOrderOmsMethod(OrderParamDto orderParamDto, UserInfo currentUser) throws Exception;

    /**
     * 淘宝试订单请求
     *
     * @param xmlStr
     * @return
     */
    Map<String, Object> dealAvailOrder(String xmlStr) throws Exception;

    /**
     * 同步淘宝信用住订单状态
     *
     * @param hotelOrderStatus
     * @return
     */
    JsonModel pushHotelOrderStatus(HotelOrderStatus hotelOrderStatus) throws Exception;

    /**
     * 淘宝信用住结账
     *
     * @param hotelOrderPay
     * @return
     */
    JsonModel dealOrderPay(HotelOrderPay hotelOrderPay) throws Exception;

    /**
     * 多房型下单
     *
     * @param order
     * @param userInfo
     * @return
     */
    Map<String, Object> dealHandMakeOrderRoomTypes(Order order, UserInfo userInfo) throws Exception;

    /**
     * 获取下单人的相关信息并存入orderParamDto中
     *
     * @param orderParamDto
     */
	void searchOperatorsInfo(OrderParamDto orderParamDto) throws Exception;

	/**
     * 统计订单数据
     * @param companyId
     * @param orderParamDto
     * @return
     */
	OrderStatisticsDto statisticsOrder(String companyId, OrderParamDto orderParamDto);

	/**
     * 设置查询订单属性
     * @param orderParamDto
	 * @param currentUser 
	 * @param operatorsJson 
	 * @param selectedOperators 
	 * @param selectStatusString 
     */
	void initFindOrderParam(OrderParamDto orderParamDto, UserInfo currentUser, String operatorsJson, String selectedOperators, String selectStatusString);
	
	/**
     * 根据订单id,统计该订单其他消费的成本
     * @param id
     * @return
     */
	List<OrderOtherPrice> statisticsOrderOtherPrice(String id);

	/**
     * 根据订单房价信息和其他消费信息统计利润
     * @param id
     * @return
     */
	BigDecimal countOrderProfit(OrderParamDto order, List<OrderOtherPrice> otherTotalCost);

    /**
     * 事件监听同步订单状态
     *
     * @param content
     */
    void eventUpdateOrderStatus(String content);
    /**
     * 处理返回被选择的订单状态字符串
     *
     * @param selectStatusString
     */
	Object handleOrderStatusString(String selectStatusString);

    /**
     * 查询所有待处理订单
     *
     * @return
     */
    List<Order> findOtaPendingOrder();

    void dealPendingOrderMethod(List<Order> orderList) throws Exception;
}

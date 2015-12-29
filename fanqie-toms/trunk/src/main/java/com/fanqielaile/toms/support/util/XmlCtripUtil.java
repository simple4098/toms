package com.fanqielaile.toms.support.util;

import com.fanqie.bean.order.CtripAvailRequestRoomPrice;
import com.fanqie.bean.order.CtripCheckRoomAvailRequest;
import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqie.bean.response.CtripHotelRoomType;
import com.fanqie.bean.response.ResponsePage;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/21.
 */
public class XmlCtripUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlDeal.class);
    private static String requestType;

    /**
     * 解析xml信息
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Element dealXmlStr(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
        return element;
    }

    /**
     * 得到xml信息的根节点名称
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static String getRootElementString(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
        return element.getName();
    }

    /**
     * 得到xml中用户信息
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static Map<String, String> getUserAndPassword(String xml) throws Exception {
        Map<String, String> map = new HashMap<>();
        Element element = dealXmlStr(xml);
        //得到头部接点
        Element headerInfo = element.element("HeaderInfo");
        //得到保存用户名和密码的节点
        Element authentication = headerInfo.element("Authentication");
        String userName = authentication.attributeValue("UserName");
        String password = authentication.attributeValue("Password");
        map.put("userName", userName);
        map.put("password", password);
        return map;
    }

    /**
     * 得到请求类型
     *
     * @return
     */
    public static String getRequestType(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        //得到头部接点
        Element headerInfo = element.element("HeaderInfo");
        requestType = headerInfo.element("RequestType").attributeValue("Name");
        return requestType;
    }

    /**
     * 得到携程试订单请求参数
     *
     * @param xml
     * @return
     */
    public static CtripCheckRoomAvailRequest getCtripCheckAvailRequest(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        //得到请求参数对象节点
        Element request = element.element("DomesticCheckRoomAvailRequest");
        CtripCheckRoomAvailRequest result = new CtripCheckRoomAvailRequest();
        result.setHotel(request.elementText("Hotel"));
        result.setArrival(TomsUtil.getDateString(request.elementText("Arrival")));
        result.setDeparture(TomsUtil.getDateString(request.elementText("Departure")));
        result.setRoom(request.elementText("Room"));
        result.setRoomNumber(request.elementText("RoomNumber"));
        result.setCurrencyCode(request.elementText("CurrencyCode"));
        result.setPerson(Integer.parseInt(request.elementText("Person")));
        result.setBalanceType(request.elementText("BalanceType"));
        result.setRoomPrices(getCtripCheckAvailRoomPrice(request.element("RoomPrices").elements("RoomPrice")));
        return result;
    }

    /**
     * 转换为toms试订单对象
     *
     * @param ctripCheckAvailRequest
     * @return
     */
    public static Order getCheckAvailOrder(CtripCheckRoomAvailRequest ctripCheckAvailRequest) {
        Order order = new Order();
        order.setInnId(ctripCheckAvailRequest.getInnId());
        order.setRoomTypeId(ctripCheckAvailRequest.getRoomTypeId().toString());
        order.setLiveTime(DateUtil.parse(TomsUtil.getDateString(ctripCheckAvailRequest.getArrival()), "yyyy-MM-dd HH:mm:ss"));
        order.setLeaveTime(DateUtil.parse(TomsUtil.getDateString(ctripCheckAvailRequest.getDeparture()), "yyyy-MM-dd HH:mm:ss"));
        order.setHomeAmount(Integer.parseInt(ctripCheckAvailRequest.getRoomNumber()));
        return order;
    }

    /**
     * 解析携程试订单每日房型信息
     *
     * @param elements
     * @return
     */
    private static List<CtripAvailRequestRoomPrice> getCtripCheckAvailRoomPrice(List<Element> elements) {
        List<CtripAvailRequestRoomPrice> ctripAvailRequestRoomPriceList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                CtripAvailRequestRoomPrice ctripAvailRequestRoomPrice = new CtripAvailRequestRoomPrice();
                ctripAvailRequestRoomPrice.setEffectDate(TomsUtil.getDateString(element.elementText("EffectDate")));
                ctripAvailRequestRoomPrice.setPrice(BigDecimal.valueOf(Double.parseDouble(element.elementText("Price"))));
                ctripAvailRequestRoomPrice.setcNYPrice(BigDecimal.valueOf(Double.parseDouble(element.elementText("CNYPrice"))));
                ctripAvailRequestRoomPrice.setCost(BigDecimal.valueOf(Double.parseDouble(element.elementText("Cost"))));
                ctripAvailRequestRoomPrice.setcNYCost(BigDecimal.valueOf(Double.parseDouble(element.elementText("CNYCost"))));
                ctripAvailRequestRoomPrice.setBreakFast(Integer.parseInt(element.elementText("BreakFast")));
                ctripAvailRequestRoomPriceList.add(ctripAvailRequestRoomPrice);
            }
        }
        return ctripAvailRequestRoomPriceList;
    }

    /**
     * 得到取消订单传入参数
     *
     * @param xml
     * @return
     */
    public static Order getCancelOrder(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        //得到请求参数节点
        Element param = element.element("DomesticCancelHotelOrderRequest");
        Order order = new Order();
        order.setChannelOrderCode(param.elementText("OrderID"));
        order.setReason(param.elementText("Notice"));
        order.setChannelSource(ChannelSource.XC);
        order.setInterFaceConfirmNO(param.elementText("InterFaceConfirmNO"));
        return order;
    }

    /**
     * 得到获取订单状态参数
     *
     * @param xml
     * @return
     */
    public static List<Order> getOrderStatus(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        Element param = element.element("DomesticGetOrderStatusRequest");
        List<Element> elements = param.element("Orders").elements("OrderEntity");
        List<Order> orderEntityList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                Order order = new Order();
                order.setChannelOrderCode(e.elementText("OrderID"));
                order.setInterFaceSendID(e.elementText("InterFaceSendID"));
                order.setChannelSource(ChannelSource.XC);
                order.setInterFaceConfirmNO("0");
                orderEntityList.add(order);
            }
        }
        return orderEntityList;
    }

    /**
     * 得到携程新增订单参数
     *
     * @param xml
     * @return
     */
    public static Order getNewOrder(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        Element param = element.element("DomesticSubmitNewHotelOrderRequest");
        Order order = new Order();
        order.setId(order.getUuid());
        //
        order.setChannelSource(ChannelSource.XC);
        //订单状态
        order.setOrderStatus(OrderStatus.ACCEPT);
        //付款状态
        order.setFeeStatus(FeeStatus.NOT_PAY);
        order.setChannelOrderCode(param.elementText("OrderID"));
        order.setInterFaceSendID(param.elementText("InterFaceSendID"));
        order.setOTAHotelId(param.elementText("Hotel"));
        order.setLiveTime(DateUtil.parse(TomsUtil.getDateString(param.elementText("Arrival")), "yyyy-MM-dd HH:mm:ss"));
        order.setLeaveTime(DateUtil.parse(TomsUtil.getDateString(param.elementText("Departure")), "yyyy-MM-dd HH:mm:ss"));
        order.setEariestArriveTime(DateUtil.parse(TomsUtil.getDateString(param.elementText("EarlyArrivalTime")), "yyyy-MM-dd HH:mm:ss"));
        order.setLastestArriveTime(DateUtil.parse(TomsUtil.getDateString(param.elementText("LastArrivalTime")), "yyyy-MM-dd HH:mm:ss"));
        order.setPerson(Integer.valueOf(param.elementText("Person")));
        order.setComment(param.elementText("Notice") + element.elementText("RemarkInfo"));
        order.setGuestMobile(param.elementText("MobilePhone"));
        String currency = param.elementText("Currency");
        if (StringUtils.isNotEmpty(currency) && "RMB".equals(currency)) {
            order.setCurrency(CurrencyCode.CNY);
        } else {
            order.setCurrency(Enum.valueOf(CurrencyCode.class, currency));
        }
        order.setPaymentType(Enum.valueOf(PaymentType.class, param.elementText("BalanceType")));
        //根据付款方式来判断订单总价
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal cNYAmount = BigDecimal.ZERO;
        if (PaymentType.FG.equals(order.getPaymentType())) {
            //现付
            amount = BigDecimal.valueOf(Double.parseDouble(param.elementText("Amount")));
            cNYAmount = BigDecimal.valueOf(Double.parseDouble(param.elementText("CNYAmount")));

        } else if (PaymentType.PP.equals(order.getPaymentType())) {
            //预付
            amount = BigDecimal.valueOf(Double.parseDouble(param.elementText("CostAmount")));
            cNYAmount = BigDecimal.valueOf(Double.parseDouble(param.elementText("CNYCostAmount")));
        }
        if (amount != BigDecimal.ZERO) {
            order.setTotalPrice(amount);
        } else {
            order.setTotalPrice(cNYAmount);
        }
        order.setBasicTotalPrice(order.getTotalPrice());
        order.setOTARoomTypeId(param.elementText("Room"));
        order.setHomeAmount(Integer.parseInt(param.elementText("Quantity")));
        order.setOrderGuestses(getOrderGuests(param.element("Guests").elements("GuestEntity"), order.getId()));
        order.setDailyInfoses(getOrderDailyInfos(param.element("RoomPrices").elements("RoomPrice"), order));
        //设置预付金额
        order.setPrepayPrice(order.getTotalPrice());
        order.setPayment(order.getTotalPrice());
        return order;
    }

    /**
     * 得到携程订单每日入住信息
     *
     * @param elements
     * @param order
     * @return
     */
    private static List<DailyInfos> getOrderDailyInfos(List<Element> elements, Order order) {
        List<DailyInfos> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                DailyInfos dailyInfos = new DailyInfos();
                dailyInfos.setOrderId(order.getId());
                dailyInfos.setDay(DateUtil.parse(TomsUtil.getDateString(element.elementText("EffectDate")), "yyyy-MM-dd"));
                //根据付款方式来判断订单每日价格
                BigDecimal amount = BigDecimal.ZERO;
                BigDecimal cNYAmount = BigDecimal.ZERO;
                if (PaymentType.FG.equals(order.getPaymentType())) {
                    //现付
                    amount = BigDecimal.valueOf(Double.parseDouble(element.elementText("Price")));
                    cNYAmount = BigDecimal.valueOf(Double.parseDouble(element.elementText("CNYPrice")));

                } else if (PaymentType.PP.equals(order.getPaymentType())) {
                    //预付
                    amount = BigDecimal.valueOf(Double.parseDouble(element.elementText("Cost")));
                    cNYAmount = BigDecimal.valueOf(Double.parseDouble(element.elementText("CNYCost")));
                }
                if (amount != BigDecimal.ZERO) {
                    dailyInfos.setPrice(amount);
                    dailyInfos.setBasicPrice(amount);
                } else {
                    dailyInfos.setPrice(cNYAmount);
                    dailyInfos.setBasicPrice(cNYAmount);
                }
                dailyInfos.setBreakfastNum(Integer.valueOf(element.elementText("BreakFast")));
                result.add(dailyInfos);
            }

        }
        return result;
    }

    /**
     * 得到携程订单入住人信息
     *
     * @param elements
     * @param orderId
     * @return
     */
    private static List<OrderGuests> getOrderGuests(List<Element> elements, String orderId) {
        List<OrderGuests> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                OrderGuests orderGuests = new OrderGuests();
                orderGuests.setOrderId(orderId);
                orderGuests.setName(element.elementText("ChinesName"));
                orderGuests.setFirstName(element.elementText("FirstName"));
                orderGuests.setLastName(element.elementText("LastName"));
                result.add(orderGuests);
            }
        }
        return result;
    }

    /**
     * 获取携程接口返回的headerInfo中的resultCode
     *
     * @param execute
     * @return
     */
    public static String getResultCode(String execute) throws Exception {
        Element element = dealXmlStr(execute);
        Element response = element.element("Response");
        String resultCode = response.element("HeaderInfo").attributeValue("ResultCode");
        return resultCode;
    }

    /**
     * 解析携程返回的xml得到携程酒店信息
     *
     * @param execute
     * @return
     */
    public static CtripHotelInfo getCtripHotelInfo(String execute) throws Exception {
        CtripHotelInfo result = new CtripHotelInfo();
        Element element = dealXmlStr(execute);
        Element response = element.element("Response").element("GetHotelInfoResponse");
        ResponsePage responsePage = new ResponsePage();
        //当前页
        responsePage.setCurrentPage(Integer.valueOf(response.elementText("CurrentPage")));
        //总页数
        responsePage.setTotalPage(Integer.valueOf(response.elementText("TotalPage")));
        //酒店总数量
        responsePage.setTotalNum(Integer.valueOf(response.elementText("TotalNum")));
        //解析hotelinfo数据
        List<Element> hotelList = response.elements("HotelList");
        List<CtripHotelInfo> ctripHotelInfos = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(hotelList.toArray())) {
            for (Element e : hotelList) {
                CtripHotelInfo ctripHotelInfo = new CtripHotelInfo();
                ctripHotelInfo.setChildHotelId(e.elementText("Hotel"));
                ctripHotelInfo.setAddress(e.elementText("Address"));
                ctripHotelInfo.setCityName(e.elementText("CityName"));
                ctripHotelInfo.setCountryName(e.elementText("CountryName"));
                ctripHotelInfo.setHotelName(e.elementText("HotelName"));
                ctripHotelInfo.setTelephone(e.elementText("Telephone"));
                ctripHotelInfos.add(ctripHotelInfo);
            }
        }
        result.setPage(responsePage);
        result.setCtripHotelInfos(ctripHotelInfos);
        return result;
    }

    /**
     * 解析携程1.2接口返回的数据
     *
     * @param execute
     * @param ctripHotelInfo
     * @return
     */
    public static CtripHotelInfo getCtripHotelInfoAndRoomTypeInfo(String execute, CtripHotelInfo ctripHotelInfo) throws Exception {
        Element element = dealXmlStr(execute);
        Element response = element.element("Response").element("GetMappingInfoResponseList");
        Element masterHorel = response.element("HotelResponseItem").element("MasterHotel");
        //得到姆酒店id
        ctripHotelInfo.setParentHotelId(masterHorel.elementText("Hotel"));
        //解析母房型信息
        List<CtripHotelRoomType> ctripHotelRoomTypeList = new ArrayList<>();
        List<Element> roomResponseItem = masterHorel.elements("RoomResponseItem");
        if (ArrayUtils.isNotEmpty(roomResponseItem.toArray())) {
            for (Element e : roomResponseItem) {
                CtripHotelRoomType ctripHotelRoomType = new CtripHotelRoomType();
                ctripHotelRoomType.setRoomTypeId(e.elementText("MasterRoom"));
                ctripHotelRoomType.setRoomTypeName(e.elementText("RoomName"));
                ctripHotelRoomTypeList.add(ctripHotelRoomType);
            }
        }
        ctripHotelInfo.setCtripHotelRoomTypeList(ctripHotelRoomTypeList);
        return ctripHotelInfo;
    }
}

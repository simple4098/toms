package com.fanqielaile.toms.support.util;

import com.fanqie.qunar.model.*;
import com.fanqie.qunar.model.BedType;
import com.fanqie.qunar.model.Breakfast;
import com.fanqie.qunar.request.Customer;
import com.fanqie.qunar.request.CustomerInfo;
import com.fanqie.qunar.request.PriceRequest;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2016/4/18.
 */
public class QunarUtil {
    private static Logger logger = LoggerFactory.getLogger(QunarUtil.class);

    /**
     * 解析去哪儿获取酒店房型信息xml
     *
     * @param xml
     * @return
     */
    public static PriceRequest getPriceRequest(String xml) {
        PriceRequest result = new PriceRequest();
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
            //得到跟节点信息
            Element element = document.getRootElement();
            result.setHotelId(element.elementText("hotelId"));
            result.setCheckIn(element.elementText("checkin"));
            result.setCheckOut(element.elementText("checkout"));
            result.setRoomId(element.elementText("roomId"));
            result.setNumberOfRooms(Integer.valueOf(element.elementText("numberOfRooms")));

        } catch (Exception e) {
            logger.info("解析去哪儿获取酒店房型信息出错" + e);
        }
        return result;
    }

    /**
     * 去哪儿创建订单
     *
     * @param xml
     * @return
     */
    public static Order getOrderDto(String xml) {
        Order order = new Order();
        try {
            Element element = XmlDeal.dealXmlStr(xml);
            order.setId(order.getUuid());
            order.setChannelSource(ChannelSource.QUNAR);
            order.setOrderStatus(OrderStatus.ACCEPT);
            order.setFeeStatus(FeeStatus.NOT_PAY);
            order.setOTAHotelId(element.elementText("hotelId"));
            order.setLiveTime(DateUtil.parse(element.elementText("checkin"), "yyyy-MM-dd"));
            order.setLeaveTime(DateUtil.parse(element.elementText("checkout"), "yyyy-MM-dd"));
            order.setTotalPrice(BigDecimal.valueOf(Double.valueOf(element.elementText("totalPrice"))));
            order.setPrepayPrice(order.getTotalPrice());
            order.setBasicTotalPrice(order.getTotalPrice());
            order.setCurrency(CurrencyCode.CNY);
            order.setComment(element.elementText("specialRemarks"));
            order.setHomeAmount(Integer.parseInt(element.elementText("numberOfRooms")));
            order.setOTARoomTypeId(element.element("room").attributeValue("id"));
            order.setRoomTypeId(order.getOTARoomTypeId());
            order.setRoomTypeName(element.element("room").attributeValue("name"));
            order.setOrderRoomTypeName(order.getRoomTypeName());
            order.setPaymentType(Enum.valueOf(PaymentType.class, element.element("room").attributeValue("payType")));
            order.setPayment(order.getTotalPrice());
            order.setChannelOrderCode(element.element("qunarOrderInfo").elementText("orderNum"));
            order.setGuestName(element.element("qunarOrderInfo").elementText("contactName"));
            order.setGuestMobile(element.element("qunarOrderInfo").elementText("contactPhone"));
            order.setGuestEmail(element.element("qunarOrderInfo").elementText("contactEmail"));
            //订单每日入住信息
            order.setDailyInfoses(getDailyInfos(order, element.element("room")));
            //订单入住人信息
            order.setOrderGuestses(getGuestses(order, element.element("customerInfos")));
        } catch (Exception e) {
            logger.info("解析去哪儿预定xml出错" + e);
        }
        return order;
    }

    /**
     * 得到去哪儿入住人信息
     *
     * @param order
     * @param customerInfos
     * @return
     */
    private static List<OrderGuests> getGuestses(Order order, Element customerInfos) {
        List<OrderGuests> result = new ArrayList<>();
        List<Element> elements = customerInfos.elements("customerInfo");
        if (null != elements && ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                List<Element> customer = e.elements("customer");
                if (null != customer && ArrayUtils.isNotEmpty(customer.toArray())) {
                    for (Element element : customer) {
                        OrderGuests guests = new OrderGuests();
                        guests.setOrderId(order.getId());
                        guests.setFirstName(element.attributeValue("firstName"));
                        guests.setLastName(element.attributeValue("lastName"));
                        guests.setNationality(element.attributeValue("nationality"));
                        if (StringUtils.isNotEmpty(element.attributeValue("seq"))) {
                            guests.setRoomPos(Integer.parseInt(element.attributeValue("seq")));
                        }
                        guests.setName(guests.getFirstName() + guests.getLastName());
                        result.add(guests);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 得到去哪儿每日入住信息
     *
     * @param order
     * @param room
     * @return
     */
    private static List<DailyInfos> getDailyInfos(Order order, Element room) {
        List<DailyInfos> dailyInfosList = new ArrayList<>();
        //得到入住和离店日期数组
        List<Date> dateList = DateUtil.getDateEntrysByDifferenceDate(order.getLiveTime(), order.getLeaveTime());
        //得到每日价格数组
        String priceString = room.attributeValue("prices");
        String prices[] = priceString.split("\\|");
        for (int i = 0; i < prices.length; i++) {
            DailyInfos infos = new DailyInfos();
            infos.setRoomTypeName(order.getRoomTypeName());
            infos.setRoomTypeNums(order.getHomeAmount());
            infos.setOrderId(order.getId());
            if (StringUtils.isNotEmpty(prices[i])) {
                infos.setBasicPrice(BigDecimal.valueOf(Double.valueOf(prices[i])));
                infos.setPrice(infos.getBasicPrice());
                infos.setRoomTypeId(order.getRoomTypeId());
                infos.setDay(dateList.get(i));
                dailyInfosList.add(infos);
            }
        }
        return dailyInfosList;
    }



    /**
     * 去哪儿取消订单，解析传入参数
     *
     * @param xml
     * @return
     */
    public static Order getCancelOrderParam(String xml) {
        Order order = new Order();
        try {
            Element element = XmlDeal.dealXmlStr(xml);
            order.setChannelOrderCode(element.elementText("qunarOrderNum"));
            order.setId(element.elementText("orderId"));
            order.setReason(element.elementText("reason"));
            order.setChannelSource(ChannelSource.QUNAR);
        } catch (Exception e) {
            logger.info("解析去哪儿取消订单xml出错" + e);
        }
        return order;
    }

    /**
     * 去哪儿查询订单 解析传入参数
     *
     * @param xml
     * @return
     */
    public static Order getQueryOrder(String xml) {
        Order order = new Order();
        try {
            Element element = XmlDeal.dealXmlStr(xml);
            order.setChannelOrderCode(element.elementText("qunarOrderNum"));
            order.setId(element.elementText("orderId"));
            order.setChannelSource(ChannelSource.QUNAR);
        } catch (Exception e) {
            logger.info("解析去哪儿查询订单xml出错" + e);
        }
        return order;
    }

    /**
     * 组装去哪儿查询订单返回参数
     *
     * @param orderInfo
     * @param xmlData
     */
    public static void dealQueryOrder(OrderInfo orderInfo, String xmlData) {
        try {
            Element element = XmlDeal.dealXmlStr(xmlData);
            orderInfo.setHotelSeq(element.element("qunarOrderInfo").elementText("hotelSeq"));
            orderInfo.setHotelName(element.element("qunarOrderInfo").elementText("hotelName"));
            orderInfo.setHotelAddress(element.element("qunarOrderInfo").elementText("hotelAddress"));
            orderInfo.setCityName(element.element("qunarOrderInfo").elementText("cityName"));
            orderInfo.setHotelPhone(element.element("qunarOrderInfo").elementText("hotelPhone"));
            orderInfo.setOrderDate(element.element("qunarOrderInfo").elementText("orderDate"));
            orderInfo.setContactName(element.element("qunarOrderInfo").elementText("contactName"));
            orderInfo.setContactPhone(element.element("qunarOrderInfo").elementText("contactPhone"));
            orderInfo.setContactEmail(element.element("qunarOrderInfo").elementText("contactEmail"));
            orderInfo.setCustomerIp(element.element("qunarOrderInfo").elementText("customerIp"));
            orderInfo.setInvoiceCode(element.element("qunarOrderInfo").elementText("invoiceCode"));
            orderInfo.setContactName(element.element("qunarOrderInfo").elementText("contactName"));
            orderInfo.setContactName(element.element("qunarOrderInfo").elementText("contactName"));
            orderInfo.setContactName(element.element("qunarOrderInfo").elementText("contactName"));
            orderInfo.setInvoice(new Invoice());
            orderInfo.setHotelId(element.elementText("hotelId"));
            orderInfo.setCheckin(element.elementText("checkin"));
            orderInfo.setCheckout(element.elementText("checkout"));
            orderInfo.setTotalPrice(BigDecimal.valueOf(Double.valueOf(element.elementText("totalPrice"))));
            orderInfo.setCurrencyCode(element.elementText("currencyCode"));
            orderInfo.setCustomerArriveTime(element.elementText("customerArriveTime"));
            orderInfo.setSpecialRemarks(element.elementText("specialRemarks"));
            Room room = new Room();
            room.setId(element.element("room").attributeValue("id"));
            room.setName(element.element("room").attributeValue("name"));
            room.setPrices(element.element("room").attributeValue("prices"));
            room.setTaxAndFee(element.element("room").attributeValue("taxAndFee"));
            room.setRoomRate(element.element("room").attributeValue("roomRate"));
            BedType bedType = new BedType();
            bedType.setBeds(getBedList(element.element("room").element("bedType").elements("beds")));
            room.setBedType(bedType);
            Meal meal = new Meal();
            Breakfast breakfast = new Breakfast();
            breakfast.setCount(element.element("room").element("meal").element("breakfast").attributeValue("count"));
            breakfast.setDesc(element.element("room").element("meal").element("breakfast").attributeValue("desc"));
            meal.setBreakfast(breakfast);
            Lunch lunch = new Lunch();
            lunch.setCount(element.element("room").element("meal").element("lunch").attributeValue("count"));
            lunch.setDesc(element.element("room").element("meal").element("lunch").attributeValue("desc"));
            meal.setLunch(lunch);
            Dinner dinner = new Dinner();
            dinner.setCount(element.element("room").element("meal").element("dinner").attributeValue("count"));
            dinner.setDesc(element.element("room").element("meal").element("dinner").attributeValue("desc"));
            meal.setDinner(dinner);
            room.setMeal(meal);
            orderInfo.setCustomerInfos(getCutomerInfoList(element.elements("customerInfos")));
            orderInfo.setRoom(room);
        } catch (Exception e) {
            logger.info("封装去哪儿查询订单返回值出错" + e);
        }
    }

    private static List<CustomerInfo> getCutomerInfoList(List<Element> customerInfos) {
        List<CustomerInfo> result = new ArrayList<>();
        if (null != customerInfos && ArrayUtils.isNotEmpty(customerInfos.toArray())) {
            for (Element element : customerInfos) {
                element = element.element("customerInfo");
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setSeq(Integer.valueOf(element.attributeValue("seq") == null ? "0" : element.attributeValue("seq")));
                customerInfo.setChildrenAges(element.attributeValue("childrenAges"));
                customerInfo.setNumberOfAdults(Integer.valueOf(element.attributeValue("numberOfAdults") == null ? "0" : element.attributeValue("numberOfAdults")));
                customerInfo.setNumberOfChildren(Integer.valueOf(element.attributeValue("numberOfChildren") == null ? "0" : element.attributeValue("numberOfChildren")));
                customerInfo.setCustomerList(getCutomerList(element.elements("customer")));
                result.add(customerInfo);
            }
        }
        return result;
    }

    /**
     * 处理去哪儿入住人信息
     *
     * @param customer
     * @return
     */
    private static List<Customer> getCutomerList(List<Element> customer) {
        List<Customer> result = new ArrayList<>();
        if (null != customer && ArrayUtils.isNotEmpty(customer.toArray())) {
            for (Element element : customer) {
                Customer customer1 = new Customer();
                customer1.setFirstName(element.attributeValue("firstName"));
                customer1.setLastName(element.attributeValue("lastName"));
                customer1.setGender(element.attributeValue("gender"));
                customer1.setNationality(element.attributeValue("nationality"));
                result.add(customer1);
            }
        }
        return result;
    }

    /**
     * 得到去哪儿床型信息
     *
     * @param elements
     * @return
     */
    private static List<Bed> getBedList(List<Element> elements) {
        List<Bed> bedList = new ArrayList<>();
        if (null != elements && ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                Bed bed = new Bed();
                bed.setSeq(Integer.valueOf(element.attributeValue("seq")));
                bed.setCount(Integer.valueOf(element.attributeValue("count")));
                bed.setCode(element.attributeValue("code"));
                bed.setDesc(element.attributeValue("desc"));
                bed.setSize(element.attributeValue("size"));
                bedList.add(bed);
            }
        }
        return bedList;
    }

    /**
     * 处理请求获取房型信息
     * @param list
     * @param roomId
     * @return
     */
    public static List<RoomTypeInfo> dealRoomTypeList(List<RoomTypeInfo> list, String roomId) {
        List<RoomTypeInfo> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            for (RoomTypeInfo roomTypeInfo : list){
                if (roomTypeInfo.getRoomTypeId().toString().equals(roomId)){
                    result.add(roomTypeInfo);
                }
            }
        }
        return result;
    }
}

package com.fanqielaile.toms.support.util;

import com.fanqie.jw.enums.OrderRequestType;
import com.fanqie.jw.enums.ResponseType;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 * 众荟xml工具类
 */
public class XmlJointWisdomUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlJointWisdomUtil.class);
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
//        Element rootElement = element.element("Body");
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
     * 得到众荟的试订单请求对象
     *
     * @param xml
     * @return
     */
    public static Order getJointWisdomAvailOrder(String xml) throws Exception {
        Order order = new Order();
        //解析xml
        Element element = dealXmlStr(xml);
//        Element element = dealXmlStr.element("OTA_HotelAvailRQ");//根节点
        Element param = element.element("AvailRequestSegments").element("AvailRequestSegment").element("HotelSearchCriteria").element("Criterion");
        //酒店的code
        order.setInnCode(param.element("HotelRef").attributeValue("HotelCode"));
        order.setLiveTime(DateUtil.parse(param.element("StayDateRange").attributeValue("Start"), "yyyy-MM-dd"));
        order.setLeaveTime(DateUtil.parse(param.element("StayDateRange").attributeValue("End"), "yyyy-MM-dd"));
        order.setRoomTypeCode(param.element("RoomStayCandidates").element("RoomStayCandidate").attributeValue("RoomTypeCode"));
        order.setHomeAmount(Integer.parseInt(param.element("RoomStayCandidates").element("RoomStayCandidate").attributeValue("Quantity")));
        order.setChannelSource(ChannelSource.ZH);
        return order;
    }

    public static void main(String[] args) {
        String xml = "<SpecialRequests>\n" +
                "\t\t\t\t\t\t\t\t<SpecialRequest>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>Please provide extra robe.</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>must be nonsmoking room</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>try to arrange nonsmoking room</Text>\n" +
                "\t\t\t\t\t\t\t\t\t<Text>extra breakfast request</Text>\n" +
                "\t\t\t\t\t\t\t\t</SpecialRequest>\n" +
                "\t\t\t\t\t\t\t</SpecialRequests>";
        try {
            Element element = dealXmlStr(xml);
            List<Element> elements = element.element("SpecialRequest").elements("Text");
            if (ArrayUtils.isNotEmpty(elements.toArray())) {
                for (Element e : elements) {
                    System.out.println(e.elementText("Text"));
                }
            }
//            Order jointWisdomAvailOrder = getJointWisdomAvailOrder(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到订单请求的类型
     *
     * @param xml
     * @return
     */
    public static OrderRequestType getOrderRequestType(String xml) throws Exception {
        Element element = dealXmlStr(xml);
        String status = element.attributeValue("ResStatus");
        return Enum.valueOf(OrderRequestType.class, status);
    }

    /**
     * 得到众荟的订单对象
     *
     * @param xml
     * @return
     */
    public static Order getAddOrder(String xml) throws Exception {
        Order order = new Order();
        order.setId(order.getUuid());
        order.setChannelSource(ChannelSource.ZH);
        //订单状态
        order.setOrderStatus(OrderStatus.ACCEPT);
        //付款状态
        order.setFeeStatus(FeeStatus.NOT_PAY);
        Element element = dealXmlStr(xml);
        Element param = element.element("HotelReservations").element("HotelReservation");
        //房型信息
        Element roomTypeParam = param.element("RoomStays").element("RoomStay");
        Element roomRate = roomTypeParam.element("RoomRates").element("RoomRate");
        order.setRoomTypeCode(roomRate.attributeValue("RoomTypeCode"));
        order.setOTARoomTypeId(order.getRoomTypeCode());
        order.setHomeAmount(Integer.parseInt(roomRate.attributeValue("NumberOfUnits")));
        //价格code
        order.setOTARateCode(roomRate.attributeValue("RatePlanCode"));
        order.setPaymentType(PaymentType.PP);
        //得到每日入住信息
        order.setDailyInfoses(getOrderDailyInfos(roomRate.element("Rates").elements("Rate"), order));
        order.setPerson(Integer.valueOf(roomTypeParam.element("GuestCounts").element("GuestCount").attributeValue("Count")));
        order.setLiveTime(DateUtil.parse(roomTypeParam.element("TimeSpan").attributeValue("Start"), "yyyy-MM-dd"));
        order.setLeaveTime(DateUtil.parse(roomTypeParam.element("TimeSpan").attributeValue("End"), "yyyy-MM-dd"));
        order.setTotalPrice(BigDecimal.valueOf(Double.valueOf(roomTypeParam.element("Total").attributeValue("AmountAfterTax"))));
        order.setBasicTotalPrice(order.getTotalPrice());
        order.setCurrency(CurrencyCode.CNY);
        order.setInnCode(roomTypeParam.element("BasicPropertyInfo").attributeValue("HotelCode"));
        order.setOTAHotelId(order.getInnCode());
        order.setComment(getComment(roomTypeParam.element("SpecialRequests").element("SpecialRequest").elements("Text")));
        //入住人信息
        Element guestParam = param.element("ResGuests").element("ResGuest");
        //最晚到店时间
        order.setLastestArriveTime(DateUtil.parse(TomsUtil.getDateString(guestParam.element("ArrivalTransport").element("TransportInfo").attributeValue("Time")), "yyyy-MM-dd HH:mm:ss"));
        //入住人信息
        order.setOrderGuestses(getOrderGuest(guestParam.element("Profiles").element("ProfileInfo").element("Profile").element("Customer").elements("PersonName"), order));
        //渠道订单号
        order.setChannelOrderCode(param.element("ResGlobalInfo").element("HotelReservationIDs").element("HotelReservationID").attributeValue("ResID_Value"));
        //设置预付金额
        order.setPrepayPrice(order.getTotalPrice());
        //设置已付金额
        order.setPayment(order.getTotalPrice());
        return order;
    }

    /**
     * 入住人信息
     *
     * @param elements
     * @param order
     * @return
     */
    private static List<OrderGuests> getOrderGuest(List<Element> elements, Order order) {
        List<OrderGuests> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                OrderGuests orderGuests = new OrderGuests();
                orderGuests.setOrderId(order.getId());
                orderGuests.setName(e.elementText("GivenName") + e.elementText("MiddleName") + e.elementText("Surname"));
                result.add(orderGuests);
            }
        }
        return result;
    }

    /**
     * 得到订单的备注信息
     *
     * @param elements
     * @return
     */
    private static String getComment(List<Element> elements) {
        String result = "";
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                result = result + element.getText();
            }
        }
        return result;
    }

    /**
     * 得到众荟每日入住信息
     *
     * @param elements
     * @param order
     * @return
     */
    private static List<DailyInfos> getOrderDailyInfos(List<Element> elements, Order order) {
        List<DailyInfos> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                DailyInfos d = new DailyInfos();
                d.setOrderId(order.getId());
                d.setDay(DateUtil.parse(element.attributeValue("EffectiveDate"), "yyyy-MM-dd"));
                d.setPrice(BigDecimal.valueOf(Double.valueOf(element.element("Base").attributeValue("AmountAfterTax"))));
                d.setBasicPrice(d.getPrice());
                result.add(d);
            }
        }
        return result;
    }

    /**
     * 得到众荟取消订单信息
     *
     * @param xml
     * @return
     */
    public static Order getCancelOrder(String xml) throws Exception {
        Order order = new Order();
        Element element = dealXmlStr(xml);
        List<Element> elements = element.element("HotelReservations").element("HotelReservation").element("ResGlobalInfo").element("HotelReservationIDs").elements("HotelReservationID");
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                String type = e.attributeValue("ResID_Type");
                if (ResponseType.R10.getText().equals(type)) {
                    order.setId(e.attributeValue("ResID_Value"));
                } else {
                    order.setChannelOrderCode(e.attributeValue("ResID_Value"));
                }
            }
        }
        order.setChannelSource(ChannelSource.ZH);
        return order;
    }
}

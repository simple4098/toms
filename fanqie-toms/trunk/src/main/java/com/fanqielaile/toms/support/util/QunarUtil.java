package com.fanqielaile.toms.support.util;

import com.fanqie.qunar.model.*;
import com.fanqie.qunar.model.BedType;
import com.fanqie.qunar.model.Breakfast;
import com.fanqie.qunar.request.Customer;
import com.fanqie.qunar.request.CustomerInfo;
import com.fanqie.qunar.request.PriceRequest;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
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
            order.setTotalPrice(BigDecimal.valueOf(Long.parseLong(element.elementText("totalPrice"))));
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
                        guests.setFirstName(element.elementText("firstName"));
                        guests.setLastName(element.elementText("lastName"));
                        guests.setNationality(element.elementText("nationality"));
                        guests.setRoomPos(Integer.parseInt(element.elementText("seq")));
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
        String prices[] = priceString.split("|");
        for (int i = 0; i < dateList.size(); i++) {
            DailyInfos infos = new DailyInfos();
            infos.setRoomTypeName(order.getRoomTypeName());
            infos.setRoomTypeNums(order.getHomeAmount());
            infos.setOrderId(order.getId());
            infos.setBasicPrice(BigDecimal.valueOf(Long.parseLong(prices[i])));
            infos.setPrice(infos.getBasicPrice());
            infos.setRoomTypeId(order.getRoomTypeId());
            infos.setDay(dateList.get(i));
            dailyInfosList.add(infos);
        }
        return dailyInfosList;
    }


    public static void main(String[] args) {
        String xml = "<bookingRequest>\n" +
                "\n" +
                "    <hotelId>16166</hotelId>\n" +
                "    <checkin>2015-02-20</checkin>\n" +
                "    <checkout>2015-02-22</checkout>\n" +
                "\n" +
                "    <totalPrice>380</totalPrice><!--  totalPrices = sum(prices) - promotionRule[@code=INSTANT_SUBTRACT]/value  -->\n" +
                "    <currencyCode>USD</currencyCode>\n" +
                "    <rmbPrice>2356.87</rmbPrice>\n" +
                "    <customerArriveTime>16:00-18:00</customerArriveTime>\n" +
                "    <specialRemarks>PREFER_NON_SMOKING,PREFER_HIGH_FLOOR</specialRemarks> <!-- preference from consumer -->\n" +
                "\n" +
                "    <numberOfRooms>2</numberOfRooms>\n" +
                "    <bedChoice>1</bedChoice>\n" +
                "    <instantConfirm>false</instantConfirm>\n" +
                "    <requiredAction>CONFIRM_ROOM_SUCCESS/CONFIRM_ROOM_FAILURE</requiredAction>\n" +
                "\n" +
                "    <room id=\"9986\" name=\"特色房\" broadband=\"FREE\" payType=\"PREPAY\" prices=\"200|200\" status=\"ACTIVE|ACTIVE\" counts=\"5|5\" \n" +
                "        roomRate=\"180|180\" taxAndFee=\"20|20\" maxOccupancy=\"2\" occupancyNumber=\"2\"\n" +
                "        freeChildrenNumber=\"1\" freeChildrenAgeLimit=\"8\" instantConfirmRoomCount=\"3|3\" wifi=\"FREE\" \n" +
                "        checkinTime=\"\" checkoutTime=\"\" area=\"\" >\n" +
                "\n" +
                "      <bedType>\n" +
                "            <beds seq=\"1\" code=\"SINGLE\" desc=\"单人床\" count=\"2\" size=\"1.2m*2m\" >\n" +
                "            </beds>\n" +
                "      </bedType>\n" +
                "      <meal>\n" +
                "            <breakfast count=\"2|2\" desc=\"self-service breakfast\" />\n" +
                "            <lunch count=\"0|0\" desc=\"\" />\n" +
                "            <dinner count=\"0|0\" desc=\"\" />\n" +
                "      </meal>\n" +
                "      <promotionRules>\n" +
                "        <promotionRule code=\"INSTANT_DEDUCT\" desc=\"立减\" value=\"20\"></promotionRule>\n" +
                "      </promotionRules>\n" +
                "    </room>\n" +
                "\n" +
                "    <customerInfos>\n" +
                "            <customerInfo seq=\"1\" numberOfAdults=\"2\" numberOfChildren=\"2\" childrenAges=\"8|12\" >\n" +
                "                <customer firstName=\"Deng\" lastName=\"Ziqiang\" nationality=\"CN\" gender=\"male\" />\n" +
                "                <customer firstName=\"Li\" lastName=\"Xuejuan\" nationality=\"CN\" gender=\"female\" />\n" +
                "                <customer firstName=\"Child\" lastName=\"One\" nationality=\"CN\" gender=\"female\" />\n" +
                "            </customerInfo>\n" +
                "            <customerInfo seq=\"2\" numberOfAdults=\"2\" numberOfChildren=\"0\" childrenAges=\"\" >\n" +
                "                <customer firstName=\"Li\" lastName=\"XoXo\" nationality=\"CN\" gender=\"male\" />\n" +
                "                <customer firstName=\"Sun\" lastName=\"MoMo\" nationality=\"CN\" gender=\"female\" />\n" +
                "            </customerInfo>\n" +
                "    </customerInfos>\n" +
                "\n" +
                "    <qunarOrderInfo>\n" +
                "        <orderNum>j3gm141219163017759</orderNum><!-- unique order id at Qunar  -->\n" +
                "        <hotelSeq>osaka_2202</hotelSeq><!-- unique id for a hotel at Qunar -->\n" +
                "        <hotelName>阪急阪神大阪国际酒店(Hotel Hankyu International)</hotelName>\n" +
                "        <hotelAddress>19-19, Chayamachi, Kita-ku, Osaka 530-0013, Japan</hotelAddress>\n" +
                "        <cityName>大阪</cityName>\n" +
                "        <hotelPhone>0081-6-63772100</hotelPhone>\n" +
                "        <orderDate>2014-12-19 16:30:17</orderDate>\n" +
                "        <contactName>张三</contactName>\n" +
                "        <contactPhone>1381****818</contactPhone>\n" +
                "        <contactEmail>miao.****@qunar.com</contactEmail>\n" +
                "        <payType>PREPAY</payType>\n" +
                "        <customerIp>103.24.27.9</customerIp>\n" +
                "        <invoiceCode>E</invoiceCode><!-- N=no require Y=paper invoice E=electronic receipt -->\n" +
                "\n" +
                "    </qunarOrderInfo>\n" +
                "\n" +
                "</bookingRequest>";

        getOrderDto(xml);
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
            orderInfo.setTotalPrice(BigDecimal.valueOf(Long.parseLong(element.elementText("totalPrice"))));
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
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setSeq(Integer.valueOf(element.attributeValue("seq")));
                customerInfo.setChildrenAges(element.elementText("childrenAges"));
                customerInfo.setNumberOfAdults(Integer.valueOf(element.elementText("numberOfAdults")));
                customerInfo.setNumberOfChildren(Integer.valueOf(element.elementText("numberOfChildren")));
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
                customer1.setFirstName(element.elementText("firstName"));
                customer1.setLastName(element.elementText("lastName"));
                customer1.setGender(element.elementText("gender"));
                customer1.setNationality(element.elementText("nationality"));
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
}

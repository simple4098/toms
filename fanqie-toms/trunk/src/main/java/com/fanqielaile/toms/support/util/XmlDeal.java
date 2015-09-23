package com.fanqielaile.toms.support.util;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.OrderGuests;
import com.fanqielaile.toms.model.fc.FcArea;
import com.fanqielaile.toms.model.fc.FcCity;
import com.fanqielaile.toms.model.fc.FcProvince;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.fc.Response;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/19.
 * xml解析
 */
public class XmlDeal {
    private static Logger logger = LoggerFactory.getLogger(XmlDeal.class);
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
     * 解析xml信息得到order对象
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Order getOrder(String xmlStr) throws Exception {
        Order order = new Order();
        Element element = dealXmlStr(xmlStr);
        order.setId(element.elementText("OrderId"));
        //获取到淘宝订单号
        String taoBaoOrderId = element.elementText("TaoBaoOrderId");
        order.setChannelOrderCode(taoBaoOrderId);
        //获取支付宝交易号
        String alipayTradeNo = element.elementText("AlipayTradeNo");
        order.setAlipayTradeNo(alipayTradeNo);
        //支付金额
        BigDecimal payment = element.elementText("Payment") == null ? new BigDecimal(0) : new BigDecimal(element.elementText("Payment")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        order.setPayment(payment);
        //取消订单原因
        order.setReason(element.elementText("Reason") == null ? "" : element.elementText("Reason"));
        return order;
    }

    /**
     * 得到天下房仓调试订单接口查询参数
     *
     * @param xml
     * @return
     */
    public static Order getCheckRoomAvailOrder(String xml) {
        Order order = new Order();
        try {
            Element element = dealXmlStr(xml);
            Element param = element.element("CheckRoomAvailRequest");
            order.setInnId(Integer.parseInt(param.elementText("SpHotelId")));
            order.setRoomTypeId(param.elementText("SpRoomTypeId"));
            order.setOTARatePlanId(param.elementText("SpRatePlanId"));
            order.setLiveTime(DateUtil.parseDate(param.elementText("CheckInDate")));
            order.setLeaveTime(DateUtil.parseDate(param.elementText("CheckOutDate")));
            order.setHomeAmount(Integer.parseInt(param.elementText("RoomNum")));
        } catch (Exception e) {
            logger.error("解析天下房仓试订单xml出错" + e.getMessage());
            throw new TomsRuntimeException("解析xml出错" + e.getMessage());
        }
        return order;
    }

    /**
     * 得到天下房仓创建订单参数
     *
     * @param xml
     * @return
     */
    public static Order getFcCreateOrder(String xml) {
        Order order = new Order();
        try {
            Element element = dealXmlStr(xml);
            Element param = element.element("CreateHotelOrderRequest");
            Element contacter = param.element("Contacter");
            order.setId(order.getUuid());
            order.setChannelSource(ChannelSource.FC);
            order.setChannelOrderCode(param.elementText("FcOrderId"));
            order.setInnId(Integer.parseInt(param.elementText("SpHotelId")));
            order.setRoomTypeId(param.elementText("SpRoomTypeId"));
            order.setOrderStatus(OrderStatus.ACCEPT);
            order.setGuestName(contacter.elementText("LinkMan"));
            order.setGuestMobile(contacter.elementText("LinkManPhone"));
            order.setGuestEmail(contacter.elementText("Email"));
            order.setHomeAmount(Integer.parseInt(param.elementText("RoomNum")));
            order.setLiveTime(DateUtil.parse(param.elementText("CheckInDate"), "yyyy-MM-dd"));
            order.setLeaveTime(DateUtil.parse(param.elementText("CheckOutDate"), "yyyy-MM-dd"));
            order.setPrepayPrice(new BigDecimal(param.elementText("TotalAmount")));
            order.setOTARoomTypeId(param.elementText("BedType"));
            if (StringUtils.isNotEmpty(param.elementText("ArrivalTime"))) {
                order.setEariestArriveTime(DateUtil.parse(param.elementText("ArrivalTime"), "yyyy-MM-dd"));
            }
            if (StringUtils.isNotEmpty(param.elementText("LatestArrivalTime"))) {
                order.setLastestArriveTime(DateUtil.parse(param.elementText("LatestArrivalTime"), "yyyy-MM-dd"));
            }
            order.setCurrency(Enum.valueOf(CurrencyType.class, param.elementText("Currency")));
            order.setFeeStatus(FeeStatus.NOT_PAY);
            //TODO 预付,成本价设置ota佣金
            order.setFcBedType(param.elementText("BedType"));
            order.setDailyInfoses(getFcDailyInfos(param.element("SalePriceList").elements("SalePriceItem"), order.getId()));
            order.setConfirmType(getFcOrderConfirmType(param.elementText("ConfirmType")));
            order.setOrderGuestses(getFcOrderGuest(param.element("GuestInfoList").elements("GuestInfo"), order.getId()));
            order.setPaymentType(PaymentType.PREPAID);
            order.setPayment(order.getTotalPrice());

        } catch (Exception e) {
            logger.error("解析天下房仓创建订单xml出错" + e.getMessage());
            throw new TomsRuntimeException("解析天下房仓创建订单xml出错" + e.getMessage());
        }
        return order;
    }

    /**
     * 得到天下房仓创建订单入住人信息
     *
     * @param elements
     * @param orderId
     * @return
     */
    private static List<OrderGuests> getFcOrderGuest(List<Element> elements, String orderId) {
        List<OrderGuests> orderGuestsList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                OrderGuests orderGuests = new OrderGuests();
                orderGuests.setRoomPos(element.attributeCount());
                orderGuests.setOrderId(orderId);
                orderGuests.setName(element.elementText("GuestName"));
                orderGuests.setNationality(element.elementText("Nationality"));
                orderGuestsList.add(orderGuests);
            }
        }
        return orderGuestsList;
    }


    /**
     * 得到天下房仓订单确认类型
     *
     * @param confirmType
     * @return
     */
    private static ConfirmType getFcOrderConfirmType(String confirmType) {
        if (StringUtils.isNotEmpty(confirmType)) {
            if ("1".equals(confirmType)) {
                return ConfirmType.ON_LINE;
            } else if ("2".equals(confirmType)) {
                return ConfirmType.ON_EMAIL;
            } else {
                return ConfirmType.OTHER;
            }
        } else {
            throw new TomsRuntimeException("天下房仓创建订单传入xml订单确认类型参数错误");
        }

    }

    /**
     * 处理天下房仓每日入住信息
     *
     * @param elements
     * @param orderId
     * @return
     */
    private static List<DailyInfos> getFcDailyInfos(List<Element> elements, String orderId) {
        List<DailyInfos> dailyInfosList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element element : elements) {
                DailyInfos dailyInfos = new DailyInfos();
                dailyInfos.setOrderId(orderId);
                dailyInfos.setPrice(new BigDecimal(element.elementText("SalePrice")));
                dailyInfos.setDay(DateUtil.parse(element.elementText("SaleDate"), "yyyy-MM-dd"));
                dailyInfos.setBreakfastType(getFcBreakFastType(element.elementText("BreakfastType")));
                dailyInfos.setBreakfastNum(Integer.parseInt(element.elementText("BreakfastNum")));
                dailyInfosList.add(dailyInfos);
            }
        }
        return dailyInfosList;
    }

    /**
     * 得到天下房仓早餐类型
     *
     * @param breakfastType
     * @return
     */
    private static FcBreakFastType getFcBreakFastType(String breakfastType) {
        if (StringUtils.isNotEmpty(breakfastType)) {
            if ("1".equals(breakfastType)) {
                return FcBreakFastType.CHINA;
            } else if ("2".equals(breakfastType)) {
                return FcBreakFastType.WEST;
            } else {
                return FcBreakFastType.AUTO;
            }
        } else {
            throw new TomsRuntimeException("天下房仓传入xml早餐类型参数错误");
        }

    }

    /**
     * 得到天下房仓取消订单查询订单状态参数
     *
     * @param xml
     * @return
     */
    public static Order getFcCancelOrder(String xml) {
        Order order = new Order();
        try {
            Element element = dealXmlStr(xml);
            Element param = element.element("CancelHotelOrderRequest");
            order.setId(param.elementText("SpOrderId"));
            order.setReason(param.elementText("CancelReason"));
        } catch (Exception e) {
            throw new TomsRuntimeException("解析天下房仓取消订单xml出错");
        }
        return order;
    }

    /**
     * 得到天下房仓订单状态查询订单状态参数
     *
     * @param xml
     * @return
     */
    public static Order getFcOrderStatus(String xml) {
        Order order = new Order();
        try {
            Element element = dealXmlStr(xml);
            Element param = element.element("GetOrderStatusRequest");
            order.setId(param.elementText("SpOrderId"));
        } catch (Exception e) {
            throw new TomsRuntimeException("解析天下房仓订单状态xml出错");
        }
        return order;
    }

    public static List<PushRoom> getPushRoom(String xml)throws Exception{
        Element element = dealXmlStr(xml);
        List<Element> elements = element.element("list").elements("RoomType");
        return  getPushRoom(elements);
    }

    public static List<PushRoom> getPushRoom( List<Element> elements)throws Exception{
        List<PushRoom> pushRoomList = new ArrayList<PushRoom>();
        PushRoom pushRoom = null;
        RoomTypeInfo roomTypeInfo = null;
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            List<RoomDetail> roomDetails;
            RoomDetail roomDetail;
            for (Element e : elements) {
                pushRoom = new PushRoom();
                roomTypeInfo = new RoomTypeInfo();
                roomDetails = new ArrayList<RoomDetail>();
                roomTypeInfo.setAccountId(Integer.valueOf(e.elementText("AccountId")));
                roomTypeInfo.setRoomTypeId(Integer.valueOf(e.elementText("RoomTypeId")));
                roomTypeInfo.setRoomTypeName(e.elementText("RoomTypeName"));
                List<Element> RoomDetailList = e.element("RoomDetails").elements("RoomDetail");
                for (Element  el: RoomDetailList){
                    roomDetail = new RoomDetail();
                    roomDetail.setRoomDate(el.elementText("RoomDate"));
                    roomDetail.setRoomPrice(Double.valueOf(el.elementText("RoomPrice")));
                    roomDetail.setPriRoomPrice(Double.valueOf(el.elementText("PriRoomPrice")));
                    roomDetail.setRoomNum(Integer.valueOf(el.elementText("RoomNum")));
                    roomDetails.add(roomDetail);
                }
                pushRoom.setRoomDetails(roomDetails);
                pushRoom.setRoomType(roomTypeInfo);
                pushRoomList.add(pushRoom);

            }
        }
        return pushRoomList;
    }

    public static List<FcCity> pareFcCity(String xml)throws Exception{
        List<FcCity> list = new ArrayList<FcCity>();
        Element element = dealXmlStr(xml);
        List<Element> elements = element.elements("City");
        FcCity fcCity = null;
        for (Element el:elements){
            fcCity = new FcCity();
            fcCity.setCityCode(el.elementText("CityCode"));
            fcCity.setCityName(el.elementText("CityName"));
            fcCity.setProvinceCode(el.elementText("ProvinceCode"));
            list.add(fcCity);
        }
        return list;
    }
    public static List<FcProvince> pareFcProvince(String xml)throws Exception{
        List<FcProvince> list = new ArrayList<FcProvince>();
        Element element = dealXmlStr(xml);
        List<Element> elements = element.elements("Province");
        FcProvince fcProvince = null;
        for (Element el:elements){
            fcProvince = new FcProvince();
            fcProvince.setProvinceName(el.elementText("ProvinceName"));
            fcProvince.setProvinceCode(el.elementText("ProvinceCode"));
            list.add(fcProvince);
        }
        return list;
    }

    public static List<FcArea> pareFcArea(String xml)throws Exception{
        List<FcArea> list = new ArrayList<FcArea>();
        Element element = dealXmlStr(xml);
        List<Element> elements = element.elements("District");
        FcArea fcArea = null;
        for (Element el:elements){
            fcArea = new FcArea();
            fcArea.setCityCode(el.elementText("CityCode"));
            fcArea.setAreaName(el.elementText("DistrictName"));
            fcArea.setAreaCode(el.elementText("DistrictCode"));
            list.add(fcArea);
        }
        return list;
    }

    public static Response pareFcResult(String xml)throws Exception{
        Response response = new Response();
        Element element = dealXmlStr(xml);
        String resultCode = element.element("ResultCode").getText();
        String resultNo = element.element("ResultNo").getText();
        String resultMsg = element.element("ResultMsg").getText();
        response.setResultCode(resultCode);
        response.setResultMsg(resultMsg);
        response.setResultNo(resultNo);

        return response;
    }

    /**
     * 天下房仓酒店信息解析
     *
     * @param document
     * @return
     */
    public static FcHotelInfoDto dealFcHotelInfo(Document document) {
        FcHotelInfoDto fcHotelInfoDto = new FcHotelInfoDto();
        Element element = document.getRootElement();
        fcHotelInfoDto.setHotelId(element.elementText("HotelId"));
        fcHotelInfoDto.setHotelName(element.elementText("HotelChnName"));
        fcHotelInfoDto.setHotelAddress(element.elementText("ChnAddress"));
        fcHotelInfoDto.setTelephone(element.elementText("Telephone"));
        fcHotelInfoDto.setWebsiteUrl(element.elementText("WebSiteURL"));
        fcHotelInfoDto.setHotelStar(Integer.parseInt(element.elementText("HotelStar")));
        fcHotelInfoDto.setCity(element.elementText("City"));
        fcHotelInfoDto.setDistinct(element.elementText("Distinct"));
        fcHotelInfoDto.setBusiness(element.elementText("Business"));
        fcHotelInfoDto.setFcRoomTypeInfos(getFcRoomTypeInfos(element, fcHotelInfoDto.getHotelId()));
        return fcHotelInfoDto;
    }

    /**
     * 天下房仓房型信息解析
     *
     * @param element
     * @param hotelId
     * @return
     */
    public static List<FcRoomTypeInfo> getFcRoomTypeInfos(Element element, String hotelId) {
        List<FcRoomTypeInfo> result = new ArrayList<>();
        List<Element> elements = element.element("Rooms").elements("RoomType");
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                FcRoomTypeInfo fcRoomTypeInfo = new FcRoomTypeInfo();
                fcRoomTypeInfo.setHotelId(hotelId);
                fcRoomTypeInfo.setRoomTypeId(e.elementText("RoomtypeId"));
                fcRoomTypeInfo.setRoomTypeName(e.elementText("RoomTypeName"));
                fcRoomTypeInfo.setBedType(e.elementText("BedType"));
                result.add(fcRoomTypeInfo);
            }
        }
        return result;
    }
}

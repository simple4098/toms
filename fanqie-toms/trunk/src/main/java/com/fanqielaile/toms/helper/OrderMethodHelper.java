package com.fanqielaile.toms.helper;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.enums.CurrencyCode;
import com.fanqielaile.toms.enums.FeeStatus;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.enums.PaymentType;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wangdayin on 2015/6/19.
 * 订单处理方法
 */
public class OrderMethodHelper {
    //得到接口信息中的用户信息
    public static UserInfo getUserNameAndPassword(String xmlStr) throws Exception {
        //根节点
        Element element = XmlDeal.dealXmlStr(xmlStr);
        //得到用户信息节点
        Element authenticationToken = element.element("AuthenticationToken");
        //得到username信息
        String username = authenticationToken.element("Username").getText();
        //得到密码信息
        String password = authenticationToken.element("Password").getText();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username);
        userInfo.setPassword(password);
        return userInfo;
    }

    /**
     * 封装订单对象
     *
     * @param element
     * @return
     */
    public static Order getOrder(Element element) throws Exception {
        Order order = new Order();
        order.setId(order.getUuid());
        order.setChannelOrderCode(element.element("TaoBaoOrderId").getText());
        order.setInnId(Integer.parseInt(element.elementText("HotelId")));
        order.setOrderStatus(OrderStatus.ACCEPT);
        order.setOTARoomTypeId(element.elementText("TaoBaoRoomTypeId"));
        order.setRoomTypeId(element.elementText("RoomTypeId"));
        order.setGuestName(element.elementText("ContactName"));
        order.setTotalPrice(new BigDecimal(element.elementText("TotalPrice")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP));
        order.setBasicTotalPrice(new BigDecimal(element.elementText("TotalPrice")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP));
        order.setHomeAmount(Integer.parseInt(element.elementText("RoomNum")));
        order.setLiveTime(new SimpleDateFormat("yyyy-MM-dd").parse(element.elementText("CheckIn")));
        order.setLeaveTime(new SimpleDateFormat("yyyy-MM-dd").parse(element.elementText("CheckOut")));
        order.setOrderTime(new Date());
        order.setOTAHotelId(element.elementText("TaoBaoHotelId"));
        order.setOTARatePlanId(element.elementText("TaoBaoRatePlanId"));
        order.setOTAGid(element.elementText("TaoBaoGid"));
        order.setEariestArriveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(element.elementText("EarliestArriveTime")));
        order.setLastestArriveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(element.elementText("LatestArriveTime")));
        order.setCurrency(CurrencyCode.CNY);
        order.setComment(element.elementText("Comment"));
        if (element.elementText("PaymentType").equals("1")) {
            order.setPaymentType(PaymentType.PREPAID);
            order.setPrepayPrice(new BigDecimal(element.elementText("TotalPrice")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP));
        } else if (element.elementText("PaymentType").equals("5")) {
            order.setPaymentType(PaymentType.NOW_PAY);
            order.setPrepayPrice(BigDecimal.valueOf(0));
        } else {
            order.setPaymentType(PaymentType.CREDIT);
            order.setPrepayPrice(BigDecimal.valueOf(0));
        }
        order.setGuestMobile(element.elementText("ContactTel"));
        order.setFeeStatus(FeeStatus.NOT_PAY);
        order.setOTARateCode(element.elementText("RatePlanCode"));
        //解析每日价格信息
        order.setDailyInfoses(getDailyInfos(element.element("DailyInfos").elements("DailyInfo"), order.getId()));
        //解析入住人信息
        order.setOrderGuestses(getOrderGuest(element.element("OrderGuests").elements("OrderGuest"), order.getId()));
        return order;
    }

    /**
     * 处理每日价格信息
     *
     * @param dailyInfos
     * @param orderId
     * @return
     * @throws Exception
     */
    private static List<DailyInfos> getDailyInfos(List<Element> dailyInfos, String orderId) throws Exception {
        List<DailyInfos> dailyInfoses = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
            for (Element e : dailyInfos) {
                DailyInfos infos = new DailyInfos();
                infos.setOrderId(orderId);
                infos.setDay(new SimpleDateFormat("yyyy-MM-dd").parse(e.elementText("Day")));
                infos.setPrice(new BigDecimal(e.elementText("Price")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP));
                infos.setBasicPrice(new BigDecimal(e.elementText("Price")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP));
                dailyInfoses.add(infos);
            }
        }
        return dailyInfoses;
    }

    /**
     * 解析入住人信息
     *
     * @param orderguests
     * @param orderId
     * @return
     */
    private static List<OrderGuests> getOrderGuest(List<Element> orderguests, String orderId) {
        List<OrderGuests> orderGuestses = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(orderguests.toArray())) {
            for (Element e : orderguests) {
                OrderGuests orderGuest = new OrderGuests();
                orderGuest.setOrderId(orderId);
                orderGuest.setName(e.elementText("Name"));
                orderGuest.setRoomPos(Integer.parseInt(e.elementText("RoomPos")));
                orderGuestses.add(orderGuest);
            }
        }
        return orderGuestses;
    }

    /**
     * 得到订单号
     *
     * @return
     */
    public static String getOrderCode() {
        String orderCode = "";
        orderCode = new Date().getTime() + "" + new Random().nextInt(10);
        return orderCode;
    }

    /**
     * 检查手动下单参数
     *
     * @param order
     * @return
     */
    public static Boolean checkHandMakeOrder(Order order, String liveTimeString, String leaveTimeString) {
        try {
            if (StringUtils.isNotEmpty(order.getGuestMobile()) && StringUtils.isNotEmpty(order.getGuestName()) && StringUtils.isNotEmpty(order.getRoomTypeId()) && StringUtils.isNotEmpty(order.getPayment().toString())
                    ) {
                return true;
            }
            if (StringUtils.isNotEmpty(liveTimeString) && StringUtils.isNotEmpty(leaveTimeString)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * 得到最大的房间库存量
     *
     * @param roomTypeInfoDtos
     * @return
     */
    public static Integer getMaxRoomNum(List<RoomTypeInfoDto> roomTypeInfoDtos, Order order) {
        int result = 99;
        if (ArrayUtils.isNotEmpty(roomTypeInfoDtos.toArray())) {
            for (RoomTypeInfoDto roomTypeInfoDto : roomTypeInfoDtos) {
                if (roomTypeInfoDto.getRoomTypeId().equals(order.getRoomTypeId())) {
                    if (roomTypeInfoDto.getMaxRoomNum() < result) {
                        result = roomTypeInfoDto.getMaxRoomNum();
                    }
                }
            }
        }
        return result;
    }

    //得到计算后的总价
    public static BigDecimal getTotalPrice(Order order) {
        BigDecimal result = BigDecimal.ZERO;
        if (ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
            for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                result = result.add(dailyInfos.getPrice());
            }
        }
        return result.multiply(new BigDecimal(order.getHomeAmount()));
    }

    /**
     * 将ota房型信息转换为toms每日价格信息
     *
     * @param roomDetails
     * @return
     */
    public static List<DailyInfos> toDailyInfos(List<RoomDetail> roomDetails) {
        List<DailyInfos> result = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(roomDetails.toArray())) {
            for (RoomDetail roomDetail : roomDetails) {
                DailyInfos dailyInfos = new DailyInfos();
                dailyInfos.setDay(DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd"));
                dailyInfos.setPrice(BigDecimal.valueOf(roomDetail.getRoomPrice()));
                dailyInfos.setRoomNum(roomDetail.getRoomNum());
                result.add(dailyInfos);
            }
        }
        return result;
    }

    public static boolean getOrderStatusByOmsPush(String status) {
        //TODO 判断oms返回订单状态是否成功
        return false;
    }
}

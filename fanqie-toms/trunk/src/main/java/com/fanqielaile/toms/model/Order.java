package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqie.core.domain.ChildOrder;
import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.domain.Person;
import com.fanqie.core.dto.CancelOrderParamDto;
import com.fanqie.core.dto.OrderParamDto;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.*;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/18.
 * 订单
 */
@XmlRootElement(name = "ORDER")
public class Order extends Domain {
    //渠道来源
    private ChannelSource channelSource;
    //渠道的订单ID（OTA订单ID）
    private String channelOrderCode;
    //订单状态
    private OrderStatus orderStatus;
    //客栈ID
    private int innId;
    //客栈房间类型ID
    private String roomTypeId;
    //客栈房型名称
    private String roomTypeName;
    //客人姓名
    private String guestName;
    //订单总价
    private BigDecimal totalPrice;
    //房间数量
    private int homeAmount;
    //入住时间
    private Date liveTime;
    //离开时间
    private Date leaveTime;
    //预付金额
    private BigDecimal prepayPrice;
    //成本价
    private BigDecimal costPrice;
    //TOMS佣金
    private BigDecimal OTAPrice;
    //下单时间（订单下单时间）
    private Date orderTime;
    //ota房间类型ID
    private String OTARoomTypeId;
    //ota酒店id
    private String OTAHotelId;
    //ota价格收益策略id
    private String OTARatePlanId;
    //ota的商品ID
    private String OTAGid;
    //最早到店时间
    private Date eariestArriveTime;
    //最晚到店时间
    private Date lastestArriveTime;
    //支付货币
    private CurrencyType currency;
    //支付方式
    private PaymentType paymentType;
    //客人联系电话
    private String guestMobile;
    //ota收益策略
    private String OTARateCode;
    //订单付款状态
    private FeeStatus feeStatus;
    //每日价格信息
    private List<DailyInfos> dailyInfoses;
    //入住人信息
    private List<OrderGuests> orderGuestses;
    //备注
    private String comment;
    //取消原因
    private String reason;
    //otaID
    private int otaId;
    //用户名
    private String vName;
    //密码
    private String vPWD;
    //支付宝交易号，28位字符
    private String alipayTradeNo;
    //支付金额
    private BigDecimal payment;
    //客栈与OMS唯一标识
    private int accountId;
    //房态更新时间
    private Date orderCreateTime;

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public int getOtaId() {
        return otaId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public void setOtaId(int otaId) {
        this.otaId = otaId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvPWD() {
        return vPWD;
    }

    public void setvPWD(String vPWD) {
        this.vPWD = vPWD;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<DailyInfos> getDailyInfoses() {
        return dailyInfoses;
    }

    public void setDailyInfoses(List<DailyInfos> dailyInfoses) {
        this.dailyInfoses = dailyInfoses;
    }

    public List<OrderGuests> getOrderGuestses() {
        return orderGuestses;
    }

    public void setOrderGuestses(List<OrderGuests> orderGuestses) {
        this.orderGuestses = orderGuestses;
    }

    @XmlElement(name = "GuestName")
    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    @XmlElement
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ChannelSource getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(ChannelSource channelSource) {
        this.channelSource = channelSource;
    }

    public String getChannelOrderCode() {
        return channelOrderCode;
    }

    public void setChannelOrderCode(String channelOrderCode) {
        this.channelOrderCode = channelOrderCode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getInnId() {
        return innId;
    }

    public void setInnId(int innId) {
        this.innId = innId;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getHomeAmount() {
        return homeAmount;
    }

    public void setHomeAmount(int homeAmount) {
        this.homeAmount = homeAmount;
    }

    public Date getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Date liveTime) {
        this.liveTime = liveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public BigDecimal getPrepayPrice() {
        return prepayPrice;
    }

    public void setPrepayPrice(BigDecimal prepayPrice) {
        this.prepayPrice = prepayPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getOTAPrice() {
        return OTAPrice;
    }

    public void setOTAPrice(BigDecimal OTAPrice) {
        this.OTAPrice = OTAPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    public String getOTARoomTypeId() {
        return OTARoomTypeId;
    }

    public void setOTARoomTypeId(String OTARoomTypeId) {
        this.OTARoomTypeId = OTARoomTypeId;
    }

    public String getOTAHotelId() {
        return OTAHotelId;
    }

    public void setOTAHotelId(String OTAHotelId) {
        this.OTAHotelId = OTAHotelId;
    }

    public String getOTARatePlanId() {
        return OTARatePlanId;
    }

    public void setOTARatePlanId(String OTARatePlanId) {
        this.OTARatePlanId = OTARatePlanId;
    }

    public String getOTAGid() {
        return OTAGid;
    }

    public void setOTAGid(String OTAGid) {
        this.OTAGid = OTAGid;
    }

    public Date getEariestArriveTime() {
        return eariestArriveTime;
    }

    public void setEariestArriveTime(Date eariestArriveTime) {
        this.eariestArriveTime = eariestArriveTime;
    }

    public Date getLastestArriveTime() {
        return lastestArriveTime;
    }

    public void setLastestArriveTime(Date lastestArriveTime) {
        this.lastestArriveTime = lastestArriveTime;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getGuestMobile() {
        return guestMobile;
    }

    public void setGuestMobile(String guestMobile) {
        this.guestMobile = guestMobile;
    }

    public String getOTARateCode() {
        return OTARateCode;
    }

    public void setOTARateCode(String OTARateCode) {
        this.OTARateCode = OTARateCode;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    /**
     * 处理取消订单传递的参数
     *
     * @param order
     * @param dictionary
     * @return
     */
    public static CancelOrderParamDto toCancelOrderParam(Order order, Dictionary dictionary) {
        CancelOrderParamDto cancelOrderParamDto = new CancelOrderParamDto();
        cancelOrderParamDto.setOtaId(Integer.parseInt(dictionary.getValue()));
        cancelOrderParamDto.setOtaOrderNo(order.getId());
        cancelOrderParamDto.setPaidAmount(order.getTotalPrice());
        cancelOrderParamDto.setvName(dictionary.getvName());
        cancelOrderParamDto.setvPWD(dictionary.getvPWD());
        return cancelOrderParamDto;
    }

    /**
     * 处理创建订单参数
     *
     * @param order
     * @param dictionary
     * @return
     */
    public static OrderParamDto toOrderParamDto(Order order, Dictionary dictionary) {
        OrderParamDto orderParamDto = new OrderParamDto();
        orderParamDto.setOtaId(Integer.parseInt(dictionary.getValue()));
        orderParamDto.setvName(dictionary.getvName());
        orderParamDto.setvPWD(dictionary.getvPWD());
        OMSOrder omsOrder = new OMSOrder();
        omsOrder.setOtaId(Integer.parseInt(dictionary.getValue()));
        omsOrder.setAccountId(order.getAccountId());
        omsOrder.setContact(order.getGuestMobile());
        omsOrder.setOperateType(1);
        omsOrder.setOtaOrderNo(order.getId());
        omsOrder.setPaidAmount(order.getPayment() == null ? new BigDecimal(0) : order.getPayment());
        omsOrder.setRemind(order.getComment());
        omsOrder.setTotalPrice(order.getTotalPrice());
        omsOrder.setRoomTypeNum(order.getHomeAmount());
        omsOrder.setTypePay(1);
        omsOrder.setUserName(order.getGuestName());
        //TODO需要传入房态更新时间
        omsOrder.setProductTime(order.getOrderCreateTime() == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderTime()) : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderCreateTime()));
        //子订单
        List<ChildOrder> childOrders = new ArrayList<>();
        if (null != order.getDailyInfoses()) {
            for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                ChildOrder childOrder = new ChildOrder();
                childOrder.setBookRoomPrice(dailyInfos.getPrice());
                childOrder.setCheckInAt(new SimpleDateFormat("yyyy-MM-dd").format(dailyInfos.getDay()));
                childOrder.setCheckOutAt(new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.addDay(dailyInfos.getDay(), 1)));
                childOrder.setRoomTypeId(order.getRoomTypeId());
                childOrder.setRoomTypeName(order.getRoomTypeName());
                childOrders.add(childOrder);
            }
        }
        omsOrder.setChildOrders(childOrders);
        //入住人信息
        List<Person> persons = new ArrayList<>();
        if (order.getOrderGuestses() != null) {
            for (OrderGuests orderGuests : order.getOrderGuestses()) {
                Person person = new Person();
                person.setName(orderGuests.getName());
                persons.add(person);
            }
        }
        omsOrder.setPersons(persons);
        orderParamDto.setOrder(omsOrder);
        return orderParamDto;
    }
}

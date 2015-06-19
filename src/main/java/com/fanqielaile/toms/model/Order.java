package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.FeeStatus;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.enums.PaymentType;
import org.dom4j.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
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
    //OTA佣金
    private BigDecimal OTAPrice;
    //下单时间（订单下单时间）
    private Date orderTime;
    //TOMS收益策略ID
    private String incomeId;
    //ota房间类型ID
    private String OTARoomTypeId;
    //ota酒店id
    private String OTAHotelId;
    //ota价格收益策略id
    private String OTARatePlanId;
    //ota的商品ID
    private String OTAGid;
    //最早到店时间
    private Date earilestArriveTime;
    //最晚到店时间
    private Date lastetArriveTime;
    //支付货币
    private String currency;
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

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
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

    public Date getEarilestArriveTime() {
        return earilestArriveTime;
    }

    public void setEarilestArriveTime(Date earilestArriveTime) {
        this.earilestArriveTime = earilestArriveTime;
    }

    public Date getLastetArriveTime() {
        return lastetArriveTime;
    }

    public void setLastetArriveTime(Date lastetArriveTime) {
        this.lastetArriveTime = lastetArriveTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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
}

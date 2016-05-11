package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqie.core.domain.ChildOrder;
import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.domain.Person;
import com.fanqie.core.dto.CancelOrderParamDto;
import com.fanqie.core.dto.OrderParamDto;
import com.fanqie.core.dto.RoomAvailParamDto;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomStatusDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    //订单下单总价
    private BigDecimal basicTotalPrice;
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
    private CurrencyCode currency;
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
    //toms订单编号
    private String orderCode;
    //公司ID
    private String companyId;
    //下单操作人
    private String userId;
    //tag-id
    private String tagId;
    //客人邮箱
    private String guestEmail;
    //特殊要求
    private String specialRequirement;
    //附加设置
    private String reservedItem;
    //天下房仓房间类型
    private String fcBedType;
    //订单确认方式
    private ConfirmType confirmType;
    //maiAccount
    private int maiAccount;

    //绑定客栈的ID
    private String bangInnId;
    //订单价格比例
    private BigDecimal percent;
    //价格模式
    private UsedPriceModel usedPriceModel;
    //增减价的值
    private BigDecimal addPrice;
    //oms订单状态
    private boolean omsOrderStatus;
    //房仓合作商的code
    private String partnerCode;
    //oms订单号
    private String omsOrderCode;
    //客栈名称
    private String orderInnName;
    //房型名称
    private String orderRoomTypeName;
    //携程订单确认号
    private String interFaceConfirmNO;
    //携程订单接口发送号
    private String interFaceSendID;
    //携程订单入住人数
    private Integer person;
    //房型code
    private String roomTypeCode;
    //酒店code
    private String innCode;
    //酒店房型id——int
    private int roomTypeIdInt;
    //异常订单
    private List<ExceptionOrder> exceptionOrderList;
    //ota订单状态
    private OtaOrderStatus otaOrderStatus;
    //其他消费
    private List<OrderOtherPrice> orderOtherPriceList;
    //操作人
    private String operator;
    //统计某订单的其他消费总成本
    private List<OrderOtherPrice> otherTotalCost;
    //某订单的利润
    private BigDecimal profit;

    //创建订单请求的xml数据
    private String xmlData;

    //订单客人所属省份
    private String guestProvince;
    //订单客人所属城市
    private String guestCity;

    //oms订单状态
    private String omsIntOrderStatus;

    public String getOmsIntOrderStatus() {
        return omsIntOrderStatus;
    }

    public void setOmsIntOrderStatus(String omsIntOrderStatus) {
        this.omsIntOrderStatus = omsIntOrderStatus;
    }


    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public List<OrderOtherPrice> getOrderOtherPriceList() {
        return orderOtherPriceList;
    }

    public void setOrderOtherPriceList(List<OrderOtherPrice> orderOtherPriceList) {
        this.orderOtherPriceList = orderOtherPriceList;
    }

    public OtaOrderStatus getOtaOrderStatus() {
        return otaOrderStatus;
    }

    public void setOtaOrderStatus(OtaOrderStatus otaOrderStatus) {
        this.otaOrderStatus = otaOrderStatus;
    }

    public List<ExceptionOrder> getExceptionOrderList() {
        return exceptionOrderList;
    }

    public void setExceptionOrderList(List<ExceptionOrder> exceptionOrderList) {
        this.exceptionOrderList = exceptionOrderList;
    }

    public int getRoomTypeIdInt() {
        return roomTypeIdInt;
    }

    public void setRoomTypeIdInt(int roomTypeIdInt) {
        this.roomTypeIdInt = roomTypeIdInt;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getInnCode() {
        return innCode;
    }

    public void setInnCode(String innCode) {
        this.innCode = innCode;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public String getInterFaceSendID() {
        return interFaceSendID;
    }

    public void setInterFaceSendID(String interFaceSendID) {
        this.interFaceSendID = interFaceSendID;
    }

    public String getInterFaceConfirmNO() {
        return interFaceConfirmNO;
    }

    public void setInterFaceConfirmNO(String interFaceConfirmNO) {
        this.interFaceConfirmNO = interFaceConfirmNO;
    }

    public String getOrderInnName() {
        return orderInnName;
    }

    public void setOrderInnName(String orderInnName) {
        this.orderInnName = orderInnName;
    }

    public String getOrderRoomTypeName() {
        return orderRoomTypeName;
    }

    public void setOrderRoomTypeName(String orderRoomTypeName) {
        this.orderRoomTypeName = orderRoomTypeName;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public boolean getOmsOrderStatus() {
        return omsOrderStatus;
    }

    public BigDecimal getBasicTotalPrice() {
        return basicTotalPrice;
    }

    public void setBasicTotalPrice(BigDecimal basicTotalPrice) {
        this.basicTotalPrice = basicTotalPrice;
    }

    public void setOmsOrderStatus(boolean omsOrderStatus) {
        this.omsOrderStatus = omsOrderStatus;
    }

    public BigDecimal getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(BigDecimal addPrice) {
        this.addPrice = addPrice;
    }

    public UsedPriceModel getUsedPriceModel() {
        return usedPriceModel;
    }

    public void setUsedPriceModel(UsedPriceModel usedPriceModel) {
        this.usedPriceModel = usedPriceModel;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public String getBangInnId() {
        return bangInnId;
    }

    public void setBangInnId(String bangInnId) {
        this.bangInnId = bangInnId;
    }

    public int getMaiAccount() {
        return maiAccount;
    }

    public void setMaiAccount(int maiAccount) {
        this.maiAccount = maiAccount;
    }

    public ConfirmType getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(ConfirmType confirmType) {
        this.confirmType = confirmType;
    }

    public String getFcBedType() {
        return fcBedType;
    }

    public void setFcBedType(String fcBedType) {
        this.fcBedType = fcBedType;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public String getReservedItem() {
        return reservedItem;
    }

    public void setReservedItem(String reservedItem) {
        this.reservedItem = reservedItem;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

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

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
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
     * @param company
     * @return
     */
    public static CancelOrderParamDto toCancelOrderParam(Order order, Company company) {
        CancelOrderParamDto cancelOrderParamDto = new CancelOrderParamDto();
        cancelOrderParamDto.setOtaId(company.getOtaId());
        cancelOrderParamDto.setOtaOrderNo(order.getChannelOrderCode());
        //取消订单传入已付金额为0
        cancelOrderParamDto.setPaidAmount(BigDecimal.ZERO);
        cancelOrderParamDto.setvName(company.getUserAccount());
        cancelOrderParamDto.setvPWD(company.getUserPassword());
        return cancelOrderParamDto;
    }

    /**
     * 处理创建订单参数
     *
     * @param order
     * @param company
     * @return
     */
    public static OrderParamDto toOrderParamDto(Order order, Company company) {
        OrderParamDto orderParamDto = new OrderParamDto();
        orderParamDto.setOtaId(company.getOtaId());
        orderParamDto.setvName(company.getUserAccount());
        orderParamDto.setvPWD(company.getUserPassword());
        OMSOrder omsOrder = new OMSOrder();
        omsOrder.setOtaId(company.getOtaId());
        omsOrder.setAccountId(order.getAccountId());
        omsOrder.setContact(order.getGuestMobile());
        omsOrder.setOperateType(1);
        omsOrder.setOtaOrderNo(order.getChannelOrderCode());
        //设置子渠道ID
        omsOrder.setChildOtaId(order.getChannelSource().name());
        //因为存在加减价，为了屏蔽老板的误解和方便对账，这里将实付价格设置为订单总价
//        omsOrder.setPaidAmount(order.getPayment() == null ? new BigDecimal(0) : order.getPayment());
        //如果是信用住，付款金额=付款金额
        omsOrder.setRemind(order.getComment());
        omsOrder.setTotalPrice(order.getTotalPrice());
        if (ChannelSource.HAND_ORDER.equals(order.getChannelSource())) {
            omsOrder.setRoomTypeNum(1);
        } else {
            omsOrder.setRoomTypeNum(order.getHomeAmount());
        }
        if (PaymentType.CREDIT.equals(order.getPaymentType())) {
            //信用住
            omsOrder.setPaidAmount(order.getPrepayPrice());
            omsOrder.setTypePay(3);
        } else {
            //预付
            omsOrder.setPaidAmount(order.getTotalPrice());
            omsOrder.setTypePay(1);
        }
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
                childOrder.setRoomTypeId(dailyInfos.getRoomTypeId());
                childOrder.setRoomTypeName(dailyInfos.getRoomTypeName());
                //设置价格计划id，oms的价格计划code
                childOrder.setRatePlanCode(order.getOTARateCode());
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

    /**
     * 同步oms设置订单总价
     *
     * @param order
     * @return
     */
    private static BigDecimal getTotalPrice(Order order) {
        BigDecimal result = BigDecimal.ZERO;
        if (ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
            for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                result = result.add(dailyInfos.getPrice());
            }
        }
        return result.multiply(new BigDecimal(order.getHomeAmount()));
    }

    /**
     * 多房型手动下单
     * @param order
     * @param roomTypeInfoDto
     * @return
     */
    public static Order makeHandOrderByRoomTypes(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        Order handOrder = new Order();
        handOrder.setId(order.getId());
        handOrder.setAccountId(order.getAccountId());
        handOrder.setChannelSource(ChannelSource.HAND_ORDER);
        if (StringUtils.isNotEmpty(order.getChannelOrderCode())) {
            handOrder.setChannelOrderCode(order.getChannelOrderCode());
        } else {
            handOrder.setChannelOrderCode(OrderMethodHelper.getOrderCode());
        }
        //手动下单将渠道订单code跟order_code设置为相同
        handOrder.setOrderCode(handOrder.getChannelOrderCode());
        handOrder.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
        handOrder.setInnId(order.getInnId());
        handOrder.setGuestName(order.getGuestName());
        handOrder.setHomeAmount(order.getHomeAmount());
        handOrder.setLiveTime(order.getLiveTime());
        handOrder.setLeaveTime(order.getLeaveTime());
        //设置价格比例
        handOrder.setPercent(order.getPercent());
        //设置价格模式
        handOrder.setUsedPriceModel(order.getUsedPriceModel());
        handOrder.setTotalPrice(getTotalPriceByRoomTypes(order, roomTypeInfoDto));
        handOrder.setBasicTotalPrice(handOrder.getTotalPrice());
        //设置成本价，总价*（1-比例）
        if (UsedPriceModel.MAI.equals(order.getUsedPriceModel())) {
            handOrder.setCostPrice(handOrder.getTotalPrice().multiply((new BigDecimal(1).subtract(order.getPercent()))));
        } else {
            handOrder.setCostPrice(handOrder.getTotalPrice());
        }
        //TODO 设置预付，成本
        handOrder.setPrepayPrice(order.getPayment());
        handOrder.setPayment(order.getPayment());
        handOrder.setOrderTime(new Date());
        handOrder.setCurrency(CurrencyCode.CNY);
        handOrder.setPaymentType(PaymentType.PREPAID);
        handOrder.setGuestMobile(order.getGuestMobile());
        handOrder.setFeeStatus(FeeStatus.PAID);
        handOrder.setComment(order.getComment());
        handOrder.setCompanyId(order.getCompanyId());
        handOrder.setUserId(order.getUserId());
        //设置房型名称
        handOrder.setOrderInnName(order.getOrderInnName());
        //每日信息
        handOrder.setDailyInfoses(getDailyInfosRoomTypes(order, roomTypeInfoDto));
        //入住人信息
        handOrder.setOrderGuestses(getOrderGuest(order));
        //订单其他消费
        handOrder.setOrderOtherPriceList(getOrderOtherPrice(order));
        return handOrder;
    }

    /**
     * 得到订单其他消费
     *
     * @param order
     * @return
     */
    private static List<OrderOtherPrice> getOrderOtherPrice(Order order) {
        if (null != order.getOrderOtherPriceList() && CollectionUtils.isNotEmpty(order.getOrderOtherPriceList())) {
            for (OrderOtherPrice o : order.getOrderOtherPriceList()) {
                o.setOrderId(order.getId());
            }
        }
        return order.getOrderOtherPriceList();
    }

    /**
     * 处理手动下单传递的参数
     *
     * @param order
     * @return
     */
    public static Order makeHandOrder(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        Order handOrder = new Order();
        handOrder.setId(order.getId());
        handOrder.setAccountId(order.getAccountId());
        handOrder.setChannelSource(ChannelSource.HAND_ORDER);
        if (StringUtils.isNotEmpty(order.getChannelOrderCode())) {
            handOrder.setChannelOrderCode(order.getChannelOrderCode());
        } else {
            handOrder.setChannelOrderCode(OrderMethodHelper.getOrderCode());
        }
        //手动下单将渠道订单code跟order_code设置为相同
        handOrder.setOrderCode(handOrder.getChannelOrderCode());
        handOrder.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
        handOrder.setInnId(order.getInnId());
        handOrder.setGuestName(order.getGuestName());
        handOrder.setRoomTypeId(order.getRoomTypeId());
        //手动下单自动拆单，默认为1
        handOrder.setHomeAmount(1);
        handOrder.setLiveTime(order.getLiveTime());
        handOrder.setLeaveTime(order.getLeaveTime());
        //设置价格比例
        handOrder.setPercent(order.getPercent());
        //设置价格模式
        handOrder.setUsedPriceModel(order.getUsedPriceModel());
        handOrder.setTotalPrice(getTotalPrice(order, roomTypeInfoDto));
        handOrder.setBasicTotalPrice(handOrder.getTotalPrice());
        //设置成本价，总价*（1-比例）
        if (UsedPriceModel.MAI.equals(order.getUsedPriceModel())) {
            handOrder.setCostPrice(handOrder.getTotalPrice().multiply((new BigDecimal(1).subtract(order.getPercent()))));
        } else {
            handOrder.setCostPrice(handOrder.getTotalPrice());
        }
        //TODO 设置预付，成本
        handOrder.setPrepayPrice(order.getPayment());
        handOrder.setPayment(order.getPayment());
        handOrder.setOrderTime(new Date());
        handOrder.setOTARoomTypeId(order.getRoomTypeId());
        handOrder.setCurrency(CurrencyCode.CNY);
        handOrder.setPaymentType(PaymentType.PREPAID);
        handOrder.setGuestMobile(order.getGuestMobile());
        handOrder.setFeeStatus(FeeStatus.PAID);
        handOrder.setComment(order.getComment());
        handOrder.setCompanyId(order.getCompanyId());
        //设置房型名称
        handOrder.setRoomTypeName(order.getRoomTypeName());
        handOrder.setOrderRoomTypeName(order.getRoomTypeName());
        handOrder.setOrderInnName(order.getOrderInnName());
        //每日信息
        handOrder.setDailyInfoses(getDailyInfos(order, roomTypeInfoDto));
        //入住人信息
        handOrder.setOrderGuestses(getOrderGuest(order));
        return handOrder;
    }

    //设置订单总价
    private static BigDecimal getTotalPrice(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        BigDecimal result = BigDecimal.ZERO;
        if (null != roomTypeInfoDto) {
            List<RoomStatusDetail> roomStatus = roomTypeInfoDto.getRoomStatus();
            if (ArrayUtils.isNotEmpty(roomStatus.toArray())) {
                RoomStatusDetail roomTypeInfoResult = null;
                for (RoomStatusDetail statusDetail : roomStatus) {
                    if (statusDetail.getRoomTypeId().toString().equals(order.getRoomTypeId())) {
                        roomTypeInfoResult = statusDetail;
                    }
                }
                if (null != roomTypeInfoResult) {
                    if (ArrayUtils.isNotEmpty(roomTypeInfoResult.getRoomDetail().toArray())) {
                        for (RoomDetail roomDetail : roomTypeInfoResult.getRoomDetail()) {
                            result = result.add(new BigDecimal(Double.toString(roomDetail.getRoomPrice())));
                        }
                    }
                }
            }
        }
        return result.multiply(new BigDecimal(order.getHomeAmount()));
    }

    //设置订单总价:多房型下单
    private static BigDecimal getTotalPriceByRoomTypes(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        BigDecimal result = BigDecimal.ZERO;
        if (null != roomTypeInfoDto) {
            List<RoomStatusDetail> roomTypeInfoResult = getRoomStatusDetails(order, roomTypeInfoDto);
            if (null != roomTypeInfoResult && ArrayUtils.isNotEmpty(roomTypeInfoResult.toArray())) {
                for (RoomStatusDetail r : roomTypeInfoResult) {
                    if (ArrayUtils.isNotEmpty(r.getRoomDetail().toArray())) {
                        for (RoomDetail roomDetail : r.getRoomDetail()) {
                            if (null != order.getDailyInfoses() && ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
                                for (DailyInfos d : order.getDailyInfoses()) {
                                    if (r.getRoomTypeId().toString().equals(d.getRoomTypeId())) {
                                        result = result.add(new BigDecimal(Double.toString(roomDetail.getRoomPrice())).multiply(BigDecimal.valueOf(d.getRoomTypeNums())));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取订单日期中的价格
     * @param order
     * @param roomTypeInfoDto
     * @return
     */
    private static List<RoomStatusDetail> getRoomStatusDetails(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        List<RoomStatusDetail> roomTypeInfoResult = new ArrayList<>();
        if (null != roomTypeInfoDto.getRoomStatus() && ArrayUtils.isNotEmpty(roomTypeInfoDto.getRoomStatus().toArray())) {
            List<RoomStatusDetail> roomStatus = roomTypeInfoDto.getRoomStatus();
            if (ArrayUtils.isNotEmpty(roomStatus.toArray())) {
                if (null != order.getDailyInfoses() && ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
                    for (DailyInfos d : order.getDailyInfoses()) {
                        for (RoomStatusDetail statusDetail : roomStatus) {
                            if (statusDetail.getRoomTypeId().toString().equals(d.getRoomTypeId())) {
                                roomTypeInfoResult.add(statusDetail);
                            }
                        }
                    }
                }
            }
        }
        return roomTypeInfoResult;
    }

    /**
     * 设置每日价格信息:多房型下单
     *
     * @param order
     * @return
     */
    public static List<DailyInfos> getDailyInfosRoomTypes(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        List<DailyInfos> dailyInfoses = new ArrayList<>();
        if (null != order.getLiveTime() && null != order.getLeaveTime()) {
            //获取选中房型的房价信息
            List<RoomStatusDetail> roomTypeInfoResult = getRoomStatusDetails(order, roomTypeInfoDto);
            //创建订单中选中入住日期的每日房价
            if (null != roomTypeInfoResult && ArrayUtils.isNotEmpty(roomTypeInfoResult.toArray())) {
                for (RoomStatusDetail r : roomTypeInfoResult) {
                    if (ArrayUtils.isNotEmpty(r.getRoomDetail().toArray())) {
                        for (RoomDetail roomDetail : r.getRoomDetail()) {
                            //拆分订单
                            if (null != order.getDailyInfoses() && ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
                                for (DailyInfos d1 : order.getDailyInfoses()) {
                                    if (d1.getRoomTypeId().equals(r.getRoomTypeId().toString())) {
                                        for (int i = 0; i < d1.getRoomTypeNums(); i++) {
                                            DailyInfos dailyInfos = new DailyInfos();
                                            dailyInfos.setOrderId(order.getId());
                                            dailyInfos.setDay(DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd"));
                                            dailyInfos.setPrice(new BigDecimal(Double.toString(roomDetail.getRoomPrice())));
                                            dailyInfos.setBasicPrice(dailyInfos.getPrice());
                                            dailyInfos.setCreatedDate(new Date());
                                            dailyInfos.setRoomTypeId(r.getRoomTypeId().toString());
                                            dailyInfos.setRoomTypeName(r.getRoomTypeName());
                                            dailyInfos.setRoomTypeNums(d1.getRoomTypeNums());
                                            dailyInfoses.add(dailyInfos);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        return dailyInfoses;
    }

    /**
     * 设置每日价格信息
     *
     * @param order
     * @return
     */
    public static List<DailyInfos> getDailyInfos(Order order, RoomTypeInfoDto roomTypeInfoDto) {
        List<DailyInfos> dailyInfoses = new ArrayList<>();
        if (null != order.getLiveTime() && null != order.getLeaveTime()) {
            //获取选中房型的房价信息
            List<RoomStatusDetail> roomStatus = roomTypeInfoDto.getRoomStatus();
            if (ArrayUtils.isNotEmpty(roomStatus.toArray())) {
                RoomStatusDetail roomTypeInfoResult = new RoomStatusDetail();
                for (RoomStatusDetail roomStatusDetail : roomStatus) {
                    if (roomStatusDetail.getRoomTypeId().toString().equals(order.getRoomTypeId())) {
                        roomTypeInfoResult = roomStatusDetail;
                    }
                }
                //创建订单中选中入住日期的每日房价
                if (null != roomTypeInfoResult) {
                    if (ArrayUtils.isNotEmpty(roomTypeInfoResult.getRoomDetail().toArray())) {
                        for (RoomDetail roomDetail : roomTypeInfoResult.getRoomDetail()) {
                            DailyInfos dailyInfos = new DailyInfos();
                            dailyInfos.setOrderId(order.getId());
                            dailyInfos.setDay(DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd"));
                            dailyInfos.setPrice(new BigDecimal(Double.toString(roomDetail.getRoomPrice())));
                            dailyInfos.setBasicPrice(dailyInfos.getPrice());
                            dailyInfos.setCreatedDate(new Date());
                            dailyInfoses.add(dailyInfos);
                        }
                    }
                }
            }
        }
        return dailyInfoses;
    }

    public static List<OrderGuests> getOrderGuest(Order order) {
        List<OrderGuests> orderGuestses = new ArrayList<>();
        OrderGuests orderGuests = new OrderGuests();
        orderGuests.setName(order.getGuestName());
        orderGuests.setOrderId(order.getId());
        orderGuests.setGuestProvince(order.getGuestProvince());
        orderGuests.setGuestCity(order.getGuestCity());
        //默认房间号排序为0
        orderGuests.setRoomPos(0);
        orderGuestses.add(orderGuests);
        return orderGuestses;
    }

    /**
     * 转换为试订单参数
     *
     * @param company
     * @param order
     * @return
     */
    public RoomAvailParamDto toRoomAvail(Company company, Order order) {
        RoomAvailParamDto roomAvailParamDto = new RoomAvailParamDto();
        roomAvailParamDto.setFrom(DateUtil.format(order.getLiveTime()));
        roomAvailParamDto.setTo(DateUtil.format(order.getLeaveTime()));
        roomAvailParamDto.setInnId(order.getInnId());
        roomAvailParamDto.setRoomTypeId(Integer.parseInt(order.getRoomTypeId()));
        roomAvailParamDto.setOtaId(company.getOtaId());
        roomAvailParamDto.setvName(company.getUserAccount());
        roomAvailParamDto.setvPWD(company.getUserPassword());
        return roomAvailParamDto;
    }

    /**
     * 转换为试订单参数
     *
     * @param company
     * @param order
     * @param dailyInfos
     * @return
     */
    public RoomAvailParamDto toRoomAvail(Company company, Order order, DailyInfos dailyInfos) {
        RoomAvailParamDto roomAvailParamDto = new RoomAvailParamDto();
        roomAvailParamDto.setFrom(DateUtil.format(dailyInfos.getDay()));
        roomAvailParamDto.setTo(DateUtil.format(dailyInfos.getDay()));
        roomAvailParamDto.setInnId(order.getInnId());
        roomAvailParamDto.setRoomTypeId(Integer.parseInt(order.getRoomTypeId()));
        roomAvailParamDto.setOtaId(company.getOtaId());
        roomAvailParamDto.setvName(company.getUserAccount());
        roomAvailParamDto.setvPWD(company.getUserPassword());
        return roomAvailParamDto;
    }


    public Order getOrderToExceptionOrder(List<Order> exceptionOrderList) {
        Order order = new Order();
        List<ExceptionOrder> exceptionOrders = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(exceptionOrderList.toArray())) {
            for (Order o : exceptionOrderList) {
                ExceptionOrder exceptionOrder = new ExceptionOrder();
                exceptionOrder.setOrderId(o.getId());
                exceptionOrders.add(exceptionOrder);
            }
        }
        order.setExceptionOrderList(exceptionOrders);
        return order;
    }

    /**
     * 转换每日价格信息
     *
     * @param order
     * @return
     */
    public Order dealDailyInfosMethod(Order order) {
        if (null != order.getDailyInfoses() && ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
            for (DailyInfos d : order.getDailyInfoses()) {
                d.setRoomTypeId(order.getRoomTypeId());
                d.setRoomTypeNums(order.getHomeAmount());
                d.setRoomTypeName(order.getOrderRoomTypeName());
            }
        }
        return order;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public List<OrderOtherPrice> getOtherTotalCost() {
		return otherTotalCost;
	}

	public void setOtherTotalCost(List<OrderOtherPrice> otherTotalCost) {
		this.otherTotalCost = otherTotalCost;
	}

    /**
     * 转换order为异常订单
     *
     * @param order
     * @return
     */
    public Order getExceptionOrderListByOrder(Order order) {
        List<ExceptionOrder> exceptionOrders = new ArrayList<>();
        ExceptionOrder exceptionOrder = new ExceptionOrder();
        exceptionOrder.setOrderId(order.getId());
        exceptionOrders.add(exceptionOrder);
        order.setExceptionOrderList(exceptionOrders);
        return order;
    }

	public String getGuestProvince() {
		return guestProvince;
	}

	public void setGuestProvince(String guestProvince) {
		this.guestProvince = guestProvince;
	}

	public String getGuestCity() {
		return guestCity;
	}

	public void setGuestCity(String guestCity) {
		this.guestCity = guestCity;
	}
}

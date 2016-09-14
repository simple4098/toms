package com.fanqielaile.toms.bo.ctrip.homestay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * oms订单order
 */
public class OmsOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "accountId不能为空")
    private Integer accountId;

    @NotNull(message = "操作类型：operateType不能为空")
    private Integer operateType = 1;// *操作类型（1：添加 2：修改 3：取消 4:查询） 默认为添加

    private String contact;// 联系人电话
    @NotNull(message = "渠道订单：otaOrderNo不能为空")
    private String otaOrderNo;

    private Integer roomTypeNum;
    private Double totalPrice;
    private Integer typePay; //（1预付、2现付）
    private String userName;

    private List<ChildOrder> childOrders;

    // 新增字段
    private List<OrderPerson> persons;

    private boolean needConfirm;// 1*订单是否需要确认

    private Double paidAmount;// 12*已付金额

    private String remind;// 提醒

    private Integer otaId;// ota渠道编号

    // 后台加入属性 对应pms下单参数
    private Integer innId;// 客栈id
    private String otaName;// ota名称

    private String innContact;//客栈前台联系电话

    private String errMsg;//下单验证未通过的错误信息

    //代销业务
    private String productTime;//ota商品当前时间（房型更新时间）代销用 yyyy-MM-dd : HH:mm:ss
    private String childOtaId;//用于代销业务，判断当前代销过订单属于哪个渠道,传递的是渠道名字
    // 目前toms会传过来对应的渠道号,如果没有oms自行处理,如果修改需要通知pms 外挂，重要的事情说三遍

    //外挂业务新增字段 用于客户端抓取携程订单后下单必须的字段
    private String hotelId;//酒店id
    private String orderId;//订单id

    //新增的OMS订单号
    private String orderNo;//oms订单号

    private Double channelAmount; //渠道价格
    private Double extraPrice; //运营额外加减价
    private String channelPricePolicy;// 价格模式
    private Double originalPrice; //房态原价（用户设置房态价格）channelAmount - extraPrice;

    private Integer jointType;//对接类型 1pms 2ebk
    private boolean autoBook;//是否自动分房
    private String jsonData;//json扩展数据

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public boolean isAutoBook() {

        return autoBook;
    }

    public void setAutoBook(boolean autoBook) {
        this.autoBook = autoBook;
    }

    public Integer getJointType() {
        return jointType;
    }

    public void setJointType(Integer jointType) {
        this.jointType = jointType;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getChannelPricePolicy() {
        return channelPricePolicy;
    }

    public void setChannelPricePolicy(String channelPricePolicy) {
        this.channelPricePolicy = channelPricePolicy;
    }

    public Double getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(Double channelAmount) {
        this.channelAmount = channelAmount;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChildOtaId() {
        return childOtaId;
    }

    public void setChildOtaId(String childOtaId) {
        this.childOtaId = childOtaId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getInnContact() {
        return innContact;
    }

    public void setInnContact(String innContact) {
        this.innContact = innContact;
    }

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }

    public String getOtaName() {
        return otaName;
    }

    public void setOtaName(String otaName) {
        this.otaName = otaName;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOtaOrderNo() {
        return otaOrderNo;
    }

    public void setOtaOrderNo(String otaOrderNo) {
        this.otaOrderNo = otaOrderNo;
    }

    public List<OrderPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<OrderPerson> persons) {
        this.persons = persons;
    }

    public boolean getNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public Integer getRoomTypeNum() {
        return roomTypeNum;
    }

    public void setRoomTypeNum(Integer roomTypeNum) {
        this.roomTypeNum = roomTypeNum;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTypePay() {
        return typePay;
    }

    public void setTypePay(Integer typePay) {
        this.typePay = typePay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ChildOrder> getChildOrders() {
        return childOrders;
    }

    public void setChildOrders(List<ChildOrder> childOrders) {
        this.childOrders = childOrders;
    }

}

package com.fanqielaile.toms.bo.ctrip.homestay;

import com.fanqielaile.toms.support.util.JodaTimeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

public class ChildOrder {
    @NotNull(message = "预定价格：bookRoomPrice不能为空")
    private Double bookRoomPrice;// 预定价格
    @NotNull(message = "入住时间：checkInAt不能为空")
    private String checkInAt;// 入住时间
    @NotNull(message = "退房时间：checkOutAt不能为空")
    private String checkOutAt;// 退房时间

    private Integer roomTypeId;// 房型编号
    @NotNull(message = "房型名称：roomTypeName不能为空")
    private String roomTypeName;// 房型名称

    //外挂过来的房型为字符串，有可能存在006这种房型，所以会存在错误
    private String wgRoomTypeId;// 房型编号
    //客户端下单提供的子订单ID
    private String thirdOrderId;//子订单id

    private String roomIds;//ota房型对应的房间数

    private Double channelAmount; //渠道价格
    private Double extraPrice; //运营额外加减价
    private Double originalPrice; //房态原价（用户设置房态价格）channelAmount - extraPrice;

    private String ratePlanCode; //当前价格计划code
    private String ratePlanConfig; //当前价格计划设置

    public String getRatePlanConfig() {
        return ratePlanConfig;
    }

    public void setRatePlanConfig(String ratePlanConfig) {
        this.ratePlanConfig = ratePlanConfig;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Double getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(Double channelAmount) {
        this.channelAmount = channelAmount;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }

    public Double getBookRoomPrice() {
        return bookRoomPrice;
    }

    public void setBookRoomPrice(Double bookRoomPrice) {
        this.bookRoomPrice = bookRoomPrice;
    }

    public String getCheckInAt() {
        if (StringUtils.isNotEmpty(this.checkInAt)) {
            return JodaTimeUtil.format(this.checkInAt);
        }
        return checkInAt;
    }

    public void setCheckInAt(String checkInAt) {
        this.checkInAt = checkInAt;
    }

    public String getCheckOutAt() {
        if (StringUtils.isNotEmpty(this.checkOutAt)) {
            return JodaTimeUtil.format(this.checkOutAt);
        }
        return checkOutAt;
    }

    public void setCheckOutAt(String checkOutAt) {
        this.checkOutAt = checkOutAt;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getWgRoomTypeId() {
        return wgRoomTypeId;
    }

    public void setWgRoomTypeId(String wgRoomTypeId) {
        this.wgRoomTypeId = wgRoomTypeId;
    }
}

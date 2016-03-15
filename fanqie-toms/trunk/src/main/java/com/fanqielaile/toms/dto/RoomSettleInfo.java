package com.fanqielaile.toms.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangdayin on 2016/3/15.
 * 淘宝信用住单间房明细
 */
public class RoomSettleInfo {
    //日历价格
    private String dailyPriceInfo;
    //房间入住时间
    private Date roomCheckIn;
    //房间离店时间
    private Date roomCheckOut;
    //房间费用总额
    private BigDecimal roomFee;
    //房间号
    private String roomNo;
    //房间杂费
    private BigDecimal roomOtherFee;
    //房间杂费明细
    private String roomOtherFeeDetail;
    //房间状态
    private int roomStatus;

    public String getDailyPriceInfo() {
        return dailyPriceInfo;
    }

    public void setDailyPriceInfo(String dailyPriceInfo) {
        this.dailyPriceInfo = dailyPriceInfo;
    }

    public Date getRoomCheckIn() {
        return roomCheckIn;
    }

    public void setRoomCheckIn(Date roomCheckIn) {
        this.roomCheckIn = roomCheckIn;
    }

    public Date getRoomCheckOut() {
        return roomCheckOut;
    }

    public void setRoomCheckOut(Date roomCheckOut) {
        this.roomCheckOut = roomCheckOut;
    }

    public BigDecimal getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(BigDecimal roomFee) {
        this.roomFee = roomFee;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public BigDecimal getRoomOtherFee() {
        return roomOtherFee;
    }

    public void setRoomOtherFee(BigDecimal roomOtherFee) {
        this.roomOtherFee = roomOtherFee;
    }

    public String getRoomOtherFeeDetail() {
        return roomOtherFeeDetail;
    }

    public void setRoomOtherFeeDetail(String roomOtherFeeDetail) {
        this.roomOtherFeeDetail = roomOtherFeeDetail;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }
}

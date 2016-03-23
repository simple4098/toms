package com.fanqielaile.toms.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2016/3/15.
 * 淘宝信用住结账对象
 */
public class HotelOrderPay {
    //客人实际离店时间
    private String checkOut;
    //此金额是否保存担保金额
    private Integer containGuarantee;
    //每日房价
    private String dailyPriceInfo;
    //备注
    private String memo;
    //oms订单号
    private String omsOrderCode;
    //杂费
    private BigDecimal otherFee;
    //杂费明细
    private String otherFeeDetail;
    //房间号
    private String roomNo;
    //淘宝订单号
    private String tid;
    //房费总额
    private BigDecimal totalRoomFee;
    //外部订单号
    private String outId;

    private List<RoomSettleInfo> roomSettleInfoList;

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getContainGuarantee() {
        return containGuarantee;
    }

    public void setContainGuarantee(int containGuarantee) {
        this.containGuarantee = containGuarantee;
    }

    public String getDailyPriceInfo() {
        return dailyPriceInfo;
    }

    public void setDailyPriceInfo(String dailyPriceInfo) {
        this.dailyPriceInfo = dailyPriceInfo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public String getOtherFeeDetail() {
        return otherFeeDetail;
    }

    public void setOtherFeeDetail(String otherFeeDetail) {
        this.otherFeeDetail = otherFeeDetail;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public BigDecimal getTotalRoomFee() {
        return totalRoomFee;
    }

    public void setTotalRoomFee(BigDecimal totalRoomFee) {
        this.totalRoomFee = totalRoomFee;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public List<RoomSettleInfo> getRoomSettleInfoList() {
        return roomSettleInfoList;
    }

    public void setRoomSettleInfoList(List<RoomSettleInfo> roomSettleInfoList) {
        this.roomSettleInfoList = roomSettleInfoList;
    }
}

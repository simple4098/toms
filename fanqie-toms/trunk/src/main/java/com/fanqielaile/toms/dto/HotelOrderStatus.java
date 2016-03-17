package com.fanqielaile.toms.dto;

import java.util.Date;

/**
 * Created by wangdayin on 2016/3/15.
 * 淘宝订单状态同步对象
 */
public class HotelOrderStatus {
    //oms订单号
    private String omsOrderCode;
    //淘宝订单号
    private String tid;
    //实际入住时间
    private Date checkinDate;
    //实际离店时间
    private Date checkoutDate;
    //操作类型
    private Integer optType;
    //入住房间号
    private String outRoomNumber;
    //无房原因描述
    private String reasonText;
    //无房原因分类
    private String reasonType;
    //
    private String outId;
    //客人实际预定房间数
    private int rooms;

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Integer getOptType() {
        if (null != optType) {
            if (3 == optType) {
                return 8;
            } else if (6 == optType) {
                return 3;
            } else if (7 == optType) {
                return 4;
            } else if (8 == optType) {
                return 5;
            }
        }
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public String getOutRoomNumber() {
        return outRoomNumber;
    }

    public void setOutRoomNumber(String outRoomNumber) {
        this.outRoomNumber = outRoomNumber;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }
}

package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2015/6/19.
 * 订单入住人信息
 */
public class OrderGuests extends Domain {
    //入住人姓名
    private String name;
    //房间序号
    private int roomPos;
    //订单ID
    private String orderId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomPos() {
        return roomPos;
    }

    public void setRoomPos(int roomPos) {
        this.roomPos = roomPos;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

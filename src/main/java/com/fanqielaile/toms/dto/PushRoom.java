package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/27
 * @version: v1.0.0
 */
public class PushRoom {
    private List<RoomDetail> roomDetails;
    private RoomTypeInfo  roomType;

    public List<RoomDetail> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(List<RoomDetail> roomDetails) {
        this.roomDetails = roomDetails;
    }

    public RoomTypeInfo getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeInfo roomType) {
        this.roomType = roomType;
    }
}

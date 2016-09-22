package com.fanqielaile.toms.bo.ctrip.homestay;

/**
 * Created by LZQ on 2016/9/2.
 */
public class GetRoomStatusBo extends RequestBean{
    private Integer roomId;
    private String startTime;
    private String endTime;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

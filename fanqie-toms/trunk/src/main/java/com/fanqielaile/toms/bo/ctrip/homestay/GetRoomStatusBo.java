package com.fanqielaile.toms.bo.ctrip.homestay;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class GetRoomStatusBo extends RequestBean{
	@ApiModelProperty(value="房型Id",required=true)
    private Integer roomId;
	@ApiModelProperty(value="开始时间",required=true)
    private String startTime;
	@ApiModelProperty(value="结束时间",required=true)
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

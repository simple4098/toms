package com.fanqielaile.toms.dto;

import java.util.List;

public class RoomStatusDetail {
	private Integer roomTypeId;
	private String roomTypeName;
	//房型价格详细信息
	private List<RoomDetail> roomDetail;
	//价格计划
    private RatePlanConfig ratePlanConfig;
	//价格计划code
	private String ratePlanCode;

	public String getRatePlanCode() {
		return ratePlanCode;
	}

	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public RatePlanConfig getRatePlanConfig() {
		return ratePlanConfig;
	}
	public void setRatePlanConfig(RatePlanConfig ratePlanConfig) {
		this.ratePlanConfig = ratePlanConfig;
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

	public List<RoomDetail> getRoomDetail() {
		return roomDetail;
	}

	public void setRoomDetail(List<RoomDetail> roomDetail) {
		this.roomDetail = roomDetail;
	}
}

package com.fanqielaile.toms.dto;

import java.util.List;

public class RoomStatusDetail {
	private Integer roomTypeId;
	private String roomTypeName;
	//房型价格详细信息
	private List<RoomDetail> roomDetail;

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

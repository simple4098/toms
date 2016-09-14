package com.fanqielaile.toms.model.oms.vo;

import java.util.List;

public class OmsRoomInfo {
	private List<OmsRoomDetail> roomDetail;
	private String roomTypeName;
	private Integer roomTypeId;
	public List<OmsRoomDetail> getRoomDetail() {
		return roomDetail;
	}
	public void setRoomDetail(List<OmsRoomDetail> roomDetail) {
		this.roomDetail = roomDetail;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
}	


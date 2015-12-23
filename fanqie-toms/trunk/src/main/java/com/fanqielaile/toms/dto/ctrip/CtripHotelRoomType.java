package com.fanqielaile.toms.dto.ctrip;

import com.fanqie.core.Domain;

public class CtripHotelRoomType extends Domain {
	

	private String ctripParentHotelId ;
	
	private String roomTypeName ;
	
	private String  roomTypeId ;
	
	private String  currency  ;
	
	private String  bedType ;

	public String getCtripParentHotelId() {
		return ctripParentHotelId;
	}

	public void setCtripParentHotelId(String ctripParentHotelId) {
		this.ctripParentHotelId = ctripParentHotelId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	
}

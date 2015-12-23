package com.fanqielaile.toms.dto.ctrip;

import com.fanqie.core.Domain;

public class CtripHotelInfo extends Domain {
	

	private String hotelName;

	private String childHotelId;

	private String parentHotelId;
	

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getChildHotelId() {
		return childHotelId;
	}

	public void setChildHotelId(String childHotelId) {
		this.childHotelId = childHotelId;
	}

	public String getParentHotelId() {
		return parentHotelId;
	}

	public void setParentHotelId(String parentHotelId) {
		this.parentHotelId = parentHotelId;
	}
	
}

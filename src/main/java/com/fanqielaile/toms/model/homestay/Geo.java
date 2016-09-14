package com.fanqielaile.toms.model.homestay;

import java.io.Serializable;

/**
 * Created by LZQ on 2016/9/2.
 */
public class Geo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double latitude;
    private Double longitude;
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
    
}

package com.fanqielaile.toms.model.homestay;

import java.io.Serializable;

public class OmsImg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgUrl;
	private String imgName;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
}


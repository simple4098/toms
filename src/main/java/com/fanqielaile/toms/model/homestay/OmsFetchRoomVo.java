package com.fanqielaile.toms.model.homestay;

import java.util.List;

public class OmsFetchRoomVo {
	/**
	 * 城市名
	 */
	private String city;
	/**
	 * 房源地址
	 */
	private String address;
	/**
	 * 床数量
	 */
	private Integer bedNum;
	/**
	 * 床型
	 */
	private String bedType;
	/**
	 * 床的长度
	 */
	private String bedLen;
	/**
	 * 床的宽度
	 */
	private String bedWid;
	/**
	 * 房屋简介
	 */
	private String roomInfo;
	/**
	 * 楼层
	 */
	private String floorNum; 
	/**
	 * 经度
	 */
	private Double latitude;
	/**
	 * 纬度
	 */
	private Double longitude;
	/**
	 * 房屋面积
	 */
	private Integer roomArea;
	
	private List<OmsImg> images;
	/**
	 * 房源名
	 */
	private String roomTypeName;
	/**
	 * 价格
	 */
	private Integer price;
	/**
	 * 房源Id
	 */
	private Integer roomTypeId;
	
	/**
	 * 客栈Id
	 */
	private Integer innId;
	/**
	 * 客栈名称
	 */
	private String innName;
	/**
	 * 修改时间
	 */
	private String updateTime;
	private Integer otaRoomTypeId;
	private Integer  status;
	/**
	 * 联系电话
	 */
	private String frontPhone;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBedNum() {
		return bedNum;
	}
	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}
	public String getBedLen() {
		return bedLen;
	}
	public void setBedLen(String bedLen) {
		this.bedLen = bedLen;
	}
	public String getBedWid() {
		return bedWid;
	}
	public void setBedWid(String bedWid) {
		this.bedWid = bedWid;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public String getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	public String getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
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
	public Integer getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(Integer roomArea) {
		this.roomArea = roomArea;
	}
	public List<OmsImg> getImages() {
		return images;
	}
	public void setImages(List<OmsImg> images) {
		this.images = images;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Integer getInnId() {
		return innId;
	}
	public void setInnId(Integer innId) {
		this.innId = innId;
	}
	public String getInnName() {
		return innName;
	}
	public void setInnName(String innName) {
		this.innName = innName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOtaRoomTypeId() {
		return otaRoomTypeId;
	}
	public void setOtaRoomTypeId(Integer otaRoomTypeId) {
		this.otaRoomTypeId = otaRoomTypeId;
	}
	public String getFrontPhone() {
		return frontPhone;
	}
	public void setFrontPhone(String frontPhone) {
		this.frontPhone = frontPhone;
	}
	
}


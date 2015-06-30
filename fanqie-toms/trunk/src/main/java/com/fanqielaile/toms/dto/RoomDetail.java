package com.fanqielaile.toms.dto;

public class RoomDetail {
	private Integer otaRoomTypeId;
	private String roomTypeName;
	private String roomDate; /* 时间 */
	private Double roomPrice; /* 房型现价 */
	private Double priRoomPrice;/* 房型原价 */
	private Integer roomNum; /* 房型剩余房量 */
	private Integer otaRoomNum;/* 分给ota剩余房量 */
	private Integer priceStatus;/* 价格状态 0-默认价 1-周末价 2-特殊价*/
	
	/*兼容美团数据格式*/
	private Integer roomPriceInt;
	private Integer priRoomPriceInt;
	private String otaRoomTypeIdstr;

	
	
	public Integer getRoomPriceInt() {
		return (Integer)roomPrice.intValue();
	}

	public Integer getPriRoomPriceInt() {
		return (Integer)priRoomPrice.intValue();
	}

	public String getOtaRoomTypeIdstr() {
		return otaRoomTypeId+"";
	}

	public Integer getPriceStatus() {
		return priceStatus;
	}

	public void setPriceStatus(Integer priceStatus) {
		this.priceStatus = priceStatus;
	}

	public RoomDetail() {
		super();
	}

	public RoomDetail(String roomDate, Double roomPrice, Double priRoomPrice,
					  Integer roomNum, Integer otaRoomNum) {
		super();
		this.roomDate = roomDate;
		this.roomPrice = roomPrice;
		this.priRoomPrice = priRoomPrice;
		this.roomNum = roomNum;
		this.otaRoomNum = otaRoomNum;
	}

	public String getRoomDate() {
		return roomDate;
	}

	public void setRoomDate(String roomDate) {
		this.roomDate = roomDate;
	}

	public Double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getOtaRoomNum() {
		return otaRoomNum;
	}

	public void setOtaRoomNum(Integer otaRoomNum) {
		this.otaRoomNum = otaRoomNum;
	}

	public Double getPriRoomPrice() {
		return priRoomPrice;
	}

	public void setPriRoomPrice(Double priRoomPrice) {
		this.priRoomPrice = priRoomPrice;
	}


	public Integer getOtaRoomTypeId() {
		return otaRoomTypeId;
	}

	public void setOtaRoomTypeId(Integer otaRoomTypeId) {
		this.otaRoomTypeId = otaRoomTypeId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}




	

	
}

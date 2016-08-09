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
	private Double  costPrice;//成本价
	private Double  priceValue;//特殊处理价格
	//TODO oms上线去掉注释
	private Double originalPrice;  //老板设置原始价格
	private Double operatorPrice;  //运营修改后的渠道价格
	/*兼容美团数据格式*/
	private Integer roomPriceInt;
	private Integer priRoomPriceInt;
	private String otaRoomTypeIdstr;
	private String status;

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getOperatorPrice() {
		return operatorPrice;
	}

	public void setOperatorPrice(Double operatorPrice) {
		this.operatorPrice = operatorPrice;
	}

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

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(Double priceValue) {
		this.priceValue = priceValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

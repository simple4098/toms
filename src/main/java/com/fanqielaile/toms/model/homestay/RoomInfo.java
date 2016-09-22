package com.fanqielaile.toms.model.homestay;

/**
 * Created by LZQ on 2016/9/2.
 */
import java.io.Serializable;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class RoomInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="房源ID",required=true)
    private Long roomId;
	@ApiModelProperty(value="房源URL",required=true)
    private String roomUrl;
	@ApiModelProperty(value="产品名称(房源名称)",required=true)
    private String name;
	@ApiModelProperty(value="房屋户型，如:三室2厅1卫",required=true)
    private String houseModel;
	@ApiModelProperty(value="房屋面积,单位:平方",required=true)
    private Integer houseSize;
	@ApiModelProperty(value="出租类型:1.整租;2.单间;3.床位",required=true)
    private Integer rentType;
	@ApiModelProperty(value="出租面积,单位平方;",required=true)
    private Integer rentSize;
	@ApiModelProperty(value="出租的床型，如：双人床1.8*2M,双人床1.5*2M/大圆床",required=false)
    private List<Bed> beds ;
	@ApiModelProperty(value="该房源累计被预定间夜数")
    private Integer bookingCount;
	@ApiModelProperty(value="该房源好评率")
    private Float goodRate;
	@ApiModelProperty(value="房屋所有者(房东)的信息")
    private Owner owner;
	
    private Integer maxGuests;
    private Integer totalFloor;
    private String floor;
    private Integer invoiceType;
    private Integer hasLandlord;
    private Geo geo;
    private List<Image> images;
    @ApiModelProperty(value="房源详细地址")
    private Address address;
    
    private List<Facility> facilities;
    @ApiModelProperty(value="最小允许预定天数")
    private Integer minBookingDays;
    @ApiModelProperty(value="最大允许预定天数")
    private Integer maxBookingDays;
    @ApiModelProperty(value="最早入住时间")
    private String checkOutTime;
    @ApiModelProperty(value="最晚入住时间")
    private String checkInTime;
    @ApiModelProperty(value="接待时间")
    private String receptionHours;

    private Deposit deposit;
    private String tradingRules;
    private Integer onlinePayRatio;
    private String traffic;
    @ApiModelProperty(value="房源描述")
    private String description;

    private Discount discount;
    private Integer refundDays;
    private Integer price;
    private String userule;
    private Integer state;
    private String surrounding;
    private String landmark;
    private Integer instantBook;
    private String updateTime;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomUrl() {
        return roomUrl;
    }

    public void setRoomUrl(String roomUrl) {
        this.roomUrl = roomUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseModel() {
        return houseModel;
    }

    public void setHouseModel(String houseModel) {
        this.houseModel = houseModel;
    }

    public Integer getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(Integer houseSize) {
        this.houseSize = houseSize;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public Integer getRentSize() {
        return rentSize;
    }

    public void setRentSize(Integer rentSize) {
        this.rentSize = rentSize;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    public Integer getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Integer bookingCount) {
        this.bookingCount = bookingCount;
    }

    public Float getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(Float goodRate) {
        this.goodRate = goodRate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Integer getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(Integer maxGuests) {
        this.maxGuests = maxGuests;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getHasLandlord() {
        return hasLandlord;
    }

    public void setHasLandlord(Integer hasLandlord) {
        this.hasLandlord = hasLandlord;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public Integer getMinBookingDays() {
        return minBookingDays;
    }

    public void setMinBookingDays(Integer minBookingDays) {
        this.minBookingDays = minBookingDays;
    }

    public Integer getMaxBookingDays() {
        return maxBookingDays;
    }

    public void setMaxBookingDays(Integer maxBookingDays) {
        this.maxBookingDays = maxBookingDays;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getReceptionHours() {
        return receptionHours;
    }

    public void setReceptionHours(String receptionHours) {
        this.receptionHours = receptionHours;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public String getTradingRules() {
        return tradingRules;
    }

    public void setTradingRules(String tradingRules) {
        this.tradingRules = tradingRules;
    }

    public Integer getOnlinePayRatio() {
        return onlinePayRatio;
    }

    public void setOnlinePayRatio(Integer onlinePayRatio) {
        this.onlinePayRatio = onlinePayRatio;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public Integer getRefundDays() {
        return refundDays;
    }

    public void setRefundDays(Integer refundDays) {
        this.refundDays = refundDays;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUserule() {
        return userule;
    }

    public void setUserule(String userule) {
        this.userule = userule;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSurrounding() {
        return surrounding;
    }

    public void setSurrounding(String surrounding) {
        this.surrounding = surrounding;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Integer getInstantBook() {
        return instantBook;
    }

    public void setInstantBook(Integer instantBook) {
        this.instantBook = instantBook;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

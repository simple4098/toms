package com.fanqielaile.toms.bo.ctrip.homestay;

import javax.validation.constraints.NotNull;

/**
 * Created by LZQ on 2016/9/2.
 */
public class BookingCheckBo extends RequestBean{
	@NotNull(message = "房型Id：roomId不能为空")
	private Integer roomId;
	@NotNull(message = "入住时间：checkIn不能为空")
    private String checkIn;
	@NotNull(message = "离开时间：checkOut不能为空")
    private String checkOut;
	@NotNull(message = "房间数量：quantity不能为空")
    private Integer quantity;
    private Integer numOfGuests;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(Integer numOfGuests) {
        this.numOfGuests = numOfGuests;
    }
}

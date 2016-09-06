package com.fanqielaile.toms.model.minsu.bo;
import java.util.Date;
import java.util.List;

import com.fanqielaile.toms.model.minsu.Deposit;
import com.fanqielaile.toms.model.minsu.Guest;

/**
 * Created by LZQ on 2016/9/2.
 */
public class SubmitOrderBo {
    private Integer roomId;
    private String ctripOrderId;
    private Date checkIn;
    private Date checkOut;
    private Integer quantity;
    private Integer totalAmount;
    private Integer onlineAmount;
    private Integer offlineAmount;
    private Deposit deposit;
    private List<Guest> guests;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCtripOrderId() {
        return ctripOrderId;
    }

    public void setCtripOrderId(String ctripOrderId) {
        this.ctripOrderId = ctripOrderId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(Integer onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public Integer getOfflineAmount() {
        return offlineAmount;
    }

    public void setOfflineAmount(Integer offlineAmount) {
        this.offlineAmount = offlineAmount;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
}

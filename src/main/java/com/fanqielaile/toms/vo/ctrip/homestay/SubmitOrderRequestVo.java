package com.fanqielaile.toms.vo.ctrip.homestay;

import java.io.Serializable;
import java.util.List;

/**
 * Create by jame
 * Date: 2016/9/6 10:36
 * Version: 1.0
 * Description: 提交订单
 * roomId		long	Y	房源ID(供应商房源ID)
 * ctripOrderId		long	Y	携程民宿订单ID
 * checkIn		DateTime	Y	入住时间
 * checkOut		DateTime	Y	离开时间
 * quantity		int	Y	房间数量
 * totalAmount		int	Y	订单总金额，单位：分；如100表示1元
 * onlineAmount		int	Y	在线支付总金额，单位：分；如100表示1元
 * offlineAmount		int	Y	线下支付总金额，单位：分；如100表示1元
 * deposit		object	Y	押金信息
 * amount	int	Y	押金金额，单位：分；如100表示1元；
 * type	int	Y	押金支付方式：1.线上支付押金；2.线下支付押金给房东
 * contacts		object	Y	联系人信息
 * name	string	Y	联系人名称
 * mobile	string	Y	联系人手机号
 * guests		object[]	Y	入住人信息
 * name	string	Y	入住人名称
 * idCode	string	N	入住人证件号码,证件号码类型根据idType决定
 * idType	int	N	入住人证件号码类型:1.身份证;2.军人证;3.护照;
 */
public class SubmitOrderRequestVo implements Serializable{
    private long roomId;
    private long ctripOrderId;
    private String checkIn;
    private String checkOut;
    private int quantity;
    private int totalAmount;
    private int onlineAmount;
    private int offlineAmount;
    private SubmitOrderDepositVo deposit;
    private SubmitOrderContactsVo contacts;
    private List<SubmitOrderGuestsVo> guests;

    public SubmitOrderRequestVo(long roomId, long ctripOrderId, String checkIn, String checkOut, int quantity, int totalAmount, int onlineAmount, int offlineAmount, SubmitOrderDepositVo deposit, SubmitOrderContactsVo contacts, List<SubmitOrderGuestsVo> guests) {
        this.roomId = roomId;
        this.ctripOrderId = ctripOrderId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.onlineAmount = onlineAmount;
        this.offlineAmount = offlineAmount;
        this.deposit = deposit;
        this.contacts = contacts;
        this.guests = guests;
    }

    public SubmitOrderRequestVo() {
        super();
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getCtripOrderId() {
        return ctripOrderId;
    }

    public void setCtripOrderId(long ctripOrderId) {
        this.ctripOrderId = ctripOrderId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(int onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public int getOfflineAmount() {
        return offlineAmount;
    }

    public void setOfflineAmount(int offlineAmount) {
        this.offlineAmount = offlineAmount;
    }

    public SubmitOrderDepositVo getDeposit() {
        return deposit;
    }

    public void setDeposit(SubmitOrderDepositVo deposit) {
        this.deposit = deposit;
    }

    public SubmitOrderContactsVo getContacts() {
        return contacts;
    }

    public void setContacts(SubmitOrderContactsVo contacts) {
        this.contacts = contacts;
    }


    public List<SubmitOrderGuestsVo> getGuests() {
        return guests;
    }

    public void setGuests(List<SubmitOrderGuestsVo> guests) {
        this.guests = guests;
    }
}

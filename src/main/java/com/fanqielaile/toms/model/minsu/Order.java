package com.fanqielaile.toms.model.minsu;

/**
 * Created by LZQ on 2016/9/2.
 */
public class Order {
    private String orderId;
    private Integer statusId;
    private Integer totalAmount;
    private Integer onlinePayment;
    private Integer offlinePayment;
    private String createTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(Integer onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Integer getOfflinePayment() {
        return offlinePayment;
    }

    public void setOfflinePayment(Integer offlinePayment) {
        this.offlinePayment = offlinePayment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

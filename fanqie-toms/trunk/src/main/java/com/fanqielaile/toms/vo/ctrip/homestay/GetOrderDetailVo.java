package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/12 9:59
 * Version: 1.0
 * Description: 阐述
 */
public class GetOrderDetailVo {
    private Long orderId;
    private Integer statusId;
    private Integer totalAmount;
    private Integer onlinePayment;
    private Integer offlinePayment;
    private String createTime;

    public GetOrderDetailVo() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

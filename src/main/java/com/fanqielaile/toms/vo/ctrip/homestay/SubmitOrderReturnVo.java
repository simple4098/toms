package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/6 10:07
 * Version: 1.0
 * Description: 提交订单返回vo
 */
public class SubmitOrderReturnVo extends ReturnBaseVo {
    private long orderId;
    private int statusId;


    public SubmitOrderReturnVo() {
        super();
    }

    public SubmitOrderReturnVo(long orderId, int statusId) {
        this.orderId = orderId;
        this.statusId = statusId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}

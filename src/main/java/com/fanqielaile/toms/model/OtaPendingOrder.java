package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OrderStatus;

/**
 * Created by wangdayin on 2016/5/27.
 */
public class OtaPendingOrder extends Domain {
    private String orderId;
    private OrderStatus modifyStatus;
    private String reasonDesc;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(OrderStatus modifyStatus) {
        this.modifyStatus = modifyStatus;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

}
